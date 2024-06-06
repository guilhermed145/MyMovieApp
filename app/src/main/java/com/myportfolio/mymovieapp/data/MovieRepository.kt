package com.myportfolio.mymovieapp.data

import com.myportfolio.mymovieapp.BuildConfig
import com.myportfolio.mymovieapp.data.database.MovieDAO
import com.myportfolio.mymovieapp.data.database.entities.MediaMediaGenreCrossRef
import com.myportfolio.mymovieapp.data.database.relations.MediaWithCastMembers
import com.myportfolio.mymovieapp.data.database.relations.MediaWithMediaGenres
import com.myportfolio.mymovieapp.data.dto.CastDTO
import com.myportfolio.mymovieapp.data.dto.GenreDTO
import com.myportfolio.mymovieapp.data.dto.MovieDetailResultDTO
import com.myportfolio.mymovieapp.data.dto.MovieResultDTO
import com.myportfolio.mymovieapp.data.dto.SeriesDetailResultDTO
import com.myportfolio.mymovieapp.data.dto.SeriesResultDTO
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre
import com.myportfolio.mymovieapp.util.Constants
import java.lang.Exception

/**
 * The Repository object for the movies data.
 */
interface MovieRepository {
    suspend fun getMoviesInCategory(categoryId: String, page: Int): List<Media>
    suspend fun getSeriesInCategory(categoryId: String, page: Int): List<Media>
    suspend fun getMovie(mediaId: String): Media
    suspend fun getSeries(mediaId: String): Media
    suspend fun getCastList(media: Media): List<CastMember>
    suspend fun getMediaGenreList(media: Media): List<MediaGenre>
    suspend fun getMediaTypeGenreList(mediaType: String): List<MediaGenre>

    suspend fun getFavorites(): List<Media>

    suspend fun isMediaInFavorites(mediaId: Int): Int

    suspend fun addToFavorites(media: Media)

    suspend fun removeFromFavorites(media: Media)
}

class MovieRepositoryImpl(
    private val moviesApiService: MoviesApiService,
    private val movieDAO: MovieDAO
) : MovieRepository {

    private val mediaBigPosterBaseUrl = "https://image.tmdb.org/t/p/original/"
    private val mediaMediumPosterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    private val mediaSmallPosterBaseUrl = "https://image.tmdb.org/t/p/w92/"
    private val apiKey = BuildConfig.API_KEY

    override suspend fun getMoviesInCategory(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getMoviesInCategory(categoryId, apiKey, page).results.map {
            it.toMedia()
        }
    }

    override suspend fun getSeriesInCategory(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getSeriesInCategory(categoryId, apiKey, page).results.map {
            it.toMedia()
        }
    }

    override suspend fun getMovie(mediaId: String): Media {
        return moviesApiService.getMovie(mediaId, apiKey).toMedia()
    }

    override suspend fun getSeries(mediaId: String): Media {
        return moviesApiService.getSeries(mediaId, apiKey).toMedia()
    }

    override suspend fun getCastList(media: Media): List<CastMember> {
        val castList: List<CastMember> = try {
            val apiResult = moviesApiService.getCastList(
                media.mediaType, media.id.toString(), apiKey
            ).cast.map {
                it.toCastMember(media.id)
            }
            apiResult
        } catch (e: Exception) {
            movieDAO.getMediaWithCastMembers(media.id).toCastList()
        }
        return castList
    }

    override suspend fun getMediaGenreList(media: Media): List<MediaGenre> {
        var genreList: MutableList<MediaGenre> = mutableListOf()
        try {
            val apiResult = getMediaTypeGenreList(media.mediaType)
            val updatedMedia: Media = if (media.mediaType == Constants.movieTypeId) {
                getMovie(media.id.toString())
            } else {
                getSeries(media.id.toString())
            }
            for (genreId in updatedMedia.genreIdList) {
                for (genre in apiResult) {
                    if (genreId == genre.id) {
                        genreList.add(genre)
                        movieDAO.insertMediaGenre(genre)
                        movieDAO.insertMediaMediaGenreCrossRef(MediaMediaGenreCrossRef(mediaId = media.id, genre.name))
                    }
                }
            }
        } catch (e: Exception) {
            genreList = movieDAO.getMediaGenresOfMedia(media.id).toGenreList().toMutableList()
        }
        return genreList
    }

    override suspend fun getMediaTypeGenreList(mediaType: String): List<MediaGenre> {
        return moviesApiService.getGenreList(mediaType, apiKey).genres.map {
            it.toMediaGenre()
        }
    }

    override suspend fun getFavorites(): List<Media> {
        val allFavorites = movieDAO.getAllMedia()
        allFavorites.forEach {
            it.genreList = movieDAO.getMediaGenresOfMedia(it.id).toGenreList()
            it.castList = movieDAO.getMediaWithCastMembers(it.id).toCastList()
        }
        return allFavorites
    }

    override suspend fun isMediaInFavorites(mediaId: Int): Int {
        return movieDAO.isMediaInFavorites(mediaId)
    }

    override suspend fun addToFavorites(media: Media) {
        movieDAO.insertMedia(media)
        media.genreList.forEach {
            movieDAO.insertMediaGenre(it)
            movieDAO.insertMediaMediaGenreCrossRef(MediaMediaGenreCrossRef(mediaId = media.id, it.name))
        }
        media.castList.forEach {
            movieDAO.insertCastMember(it)
        }
    }

    override suspend fun removeFromFavorites(media: Media) {
        movieDAO.removeMedia(media)
        media.genreList.forEach {
            movieDAO.removeMediaGenre(it)
            movieDAO.removeMediaMediaGenreCrossRef(MediaMediaGenreCrossRef(mediaId = media.id, it.name))
        }
        media.castList.forEach {
            movieDAO.removeCastMember(it)
        }
    }

    private fun MovieResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = title,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            mediumPoster = mediaMediumPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(release_date),
            synopsis = overview,
            genreIdList = genre_ids,
            mediaType = Constants.movieTypeId
        )

    private fun SeriesResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = name,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            mediumPoster = mediaMediumPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(first_air_date),
            synopsis = overview,
            genreIdList = genre_ids,
            mediaType = Constants.seriesTypeId
        )

    private fun MovieDetailResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = original_title,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            mediumPoster = mediaMediumPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(release_date),
            synopsis = overview,
            genreIdList = genres.map {
                it.id
            },
            mediaType = Constants.seriesTypeId
        )

    private fun SeriesDetailResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = name,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            mediumPoster = mediaMediumPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(first_air_date),
            synopsis = overview,
            genreIdList = genres.map {
                it.id
            },
            mediaType = Constants.seriesTypeId
        )

    private fun toReleaseYear(releaseDate: String): Int {
        return releaseDate.take(4).toInt()
    }

    private fun GenreDTO.toMediaGenre() =
        MediaGenre(
            id = id,
            name = name
        )

    private fun CastDTO.toCastMember(mediaId: Int): CastMember =
        CastMember(
            actorImage = mediaSmallPosterBaseUrl + profile_path,
            actorName = name,
            characterName = character,
            mediaId = mediaId
        )

}

private fun List<MediaWithCastMembers>.toCastList(): List<CastMember> {
    val mediaWithCastMembers: MediaWithCastMembers = this.firstOrNull() ?: MediaWithCastMembers(
        Media(),
        listOf()
    )
    return mediaWithCastMembers.castMembers
}

private fun List<MediaWithMediaGenres>.toGenreList(): List<MediaGenre> {
    val mediaWithMediaGenres: MediaWithMediaGenres = this.firstOrNull() ?: MediaWithMediaGenres(
        Media(),
        listOf()
    )
    return mediaWithMediaGenres.genres
}
