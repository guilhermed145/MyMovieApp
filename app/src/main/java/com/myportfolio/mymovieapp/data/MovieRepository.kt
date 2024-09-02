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

// The Repository object for retrieving movies and tv series data.
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

// The implementation of the MovieRepository interface.
class MovieRepositoryImpl(
    private val moviesApiService: MoviesApiService,
    private val movieDAO: MovieDAO
) : MovieRepository {

    // Base URL for retrieving a media poster image from the API.
    private val mediaBigPosterBaseUrl = "https://image.tmdb.org/t/p/original/"
    private val mediaMediumPosterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    private val mediaSmallPosterBaseUrl = "https://image.tmdb.org/t/p/w92/"
    // API key number used to access online data.
    private val apiKey = BuildConfig.API_KEY

    // Returns a list of movie data from a specific media category.
    override suspend fun getMoviesInCategory(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getMoviesInCategory(categoryId, apiKey, page).results.map {
            it.toMedia()
        }
    }

    // Returns a list of tv series data from a specific media category.
    override suspend fun getSeriesInCategory(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getSeriesInCategory(categoryId, apiKey, page).results.map {
            it.toMedia()
        }
    }

    // Returns detailed data about a specific movie.
    override suspend fun getMovie(mediaId: String): Media {
        return moviesApiService.getMovie(mediaId, apiKey).toMedia()
    }

    // Returns detailed data about a specific tv series.
    override suspend fun getSeries(mediaId: String): Media {
        return moviesApiService.getSeries(mediaId, apiKey).toMedia()
    }

    // Returns a list of cast members from a specific media.
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

    // Returns a list of genres from a specific media.
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

    // Returns a list of all genres of a certain media type (movie or tv series).
    override suspend fun getMediaTypeGenreList(mediaType: String): List<MediaGenre> {
        return moviesApiService.getGenreList(mediaType, apiKey).genres.map {
            it.toMediaGenre()
        }
    }

    // Returns a list of all the user's favorite medias.
    override suspend fun getFavorites(): List<Media> {
        val allFavorites = movieDAO.getAllMedia()
        allFavorites.forEach {
            it.genreList = movieDAO.getMediaGenresOfMedia(it.id).toGenreList()
            it.castList = movieDAO.getMediaWithCastMembers(it.id).toCastList()
        }
        return allFavorites
    }

    // Returns true if the media specified is listed as a favorite media.
    override suspend fun isMediaInFavorites(mediaId: Int): Int {
        return movieDAO.isMediaInFavorites(mediaId)
    }

    // Adds a specified media to the favorites list.
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

    // Removes a specified media from the favorites list.
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

    // Converts MovieResultDTO objects to Media objects.
    private fun MovieResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = title,
            bigPoster = mediaBigPosterBaseUrl + posterPath,
            mediumPoster = mediaMediumPosterBaseUrl + posterPath,
            smallPoster = mediaSmallPosterBaseUrl + posterPath,
            releaseYear = toReleaseYear(releaseDate),
            synopsis = overview,
            genreIdList = genreIds,
            mediaType = Constants.movieTypeId
        )

    // Converts SeriesResultDTO objects to Media objects.
    private fun SeriesResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = name,
            bigPoster = mediaBigPosterBaseUrl + posterPath,
            mediumPoster = mediaMediumPosterBaseUrl + posterPath,
            smallPoster = mediaSmallPosterBaseUrl + posterPath,
            releaseYear = toReleaseYear(firstAirDate),
            synopsis = overview,
            genreIdList = genreIds,
            mediaType = Constants.seriesTypeId
        )

    // Converts MovieDetailResultDTO objects to Media objects.
    private fun MovieDetailResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = originalTitle,
            bigPoster = mediaBigPosterBaseUrl + posterPath,
            mediumPoster = mediaMediumPosterBaseUrl + posterPath,
            smallPoster = mediaSmallPosterBaseUrl + posterPath,
            releaseYear = toReleaseYear(releaseDate),
            synopsis = overview,
            genreIdList = genres.map {
                it.id
            },
            mediaType = Constants.seriesTypeId
        )

    // Converts SeriesDetailResultDTO objects to Media objects.
    private fun SeriesDetailResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = name,
            bigPoster = mediaBigPosterBaseUrl + posterPath,
            mediumPoster = mediaMediumPosterBaseUrl + posterPath,
            smallPoster = mediaSmallPosterBaseUrl + posterPath,
            releaseYear = toReleaseYear(firstAirDate),
            synopsis = overview,
            genreIdList = genres.map {
                it.id
            },
            mediaType = Constants.seriesTypeId
        )

    // Converts a media release date string found in the API JSON to a integer value that represents the year the media was released.
    private fun toReleaseYear(releaseDate: String): Int {
        return releaseDate.take(4).toInt()
    }

    // Converts GenreDTO objects to MediaGenre objects.
    private fun GenreDTO.toMediaGenre() =
        MediaGenre(
            id = id,
            name = name
        )

    // Converts CastDTO objects to CastMember objects.
    private fun CastDTO.toCastMember(mediaId: Int): CastMember =
        CastMember(
            actorImage = mediaSmallPosterBaseUrl + profilePath,
            actorName = name,
            characterName = character,
            mediaId = mediaId
        )

}

// Receives a list of MediaWithCastMembers object and returns a list of cast members of each media.
private fun List<MediaWithCastMembers>.toCastList(): List<CastMember> {
    val mediaWithCastMembers: MediaWithCastMembers = this.firstOrNull() ?: MediaWithCastMembers(
        Media(),
        listOf()
    )
    return mediaWithCastMembers.castMembers
}

// Receives a list of MediaWithMediaGenres object and returns a list of genres of each media.
private fun List<MediaWithMediaGenres>.toGenreList(): List<MediaGenre> {
    val mediaWithMediaGenres: MediaWithMediaGenres = this.firstOrNull() ?: MediaWithMediaGenres(
        Media(),
        listOf()
    )
    return mediaWithMediaGenres.genres
}
