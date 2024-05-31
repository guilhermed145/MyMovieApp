package com.myportfolio.mymovieapp.data

import com.myportfolio.mymovieapp.BuildConfig
import com.myportfolio.mymovieapp.data.database.MovieDAO
import com.myportfolio.mymovieapp.data.dto.CastDTO
import com.myportfolio.mymovieapp.data.dto.GenreDTO
import com.myportfolio.mymovieapp.data.dto.MovieResultDTO
import com.myportfolio.mymovieapp.data.dto.SerieResultDTO
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre
import com.myportfolio.mymovieapp.util.Constants

/**
 * The Repository object for the movies data.
 */
interface MovieRepository {
    suspend fun getMovies(categoryId: String, page: Int): List<Media>
    suspend fun getSeries(categoryId: String, page: Int): List<Media>
    suspend fun getCastList(categoryId: String, mediaId: Int): List<CastMember>
    suspend fun getGenreList(mediaType: String): List<MediaGenre>

    suspend fun getFavorites(): List<Media>

    suspend fun getFavoriteMedia(mediaId: Int): Media

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

    override suspend fun getMovies(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getMovies(categoryId, apiKey, page).results.map {
//            val movieCastList = getCastList(categoryId, it.id)
//            val movieGenreList = getGenreList(Constants.movieTypeId).getGenreNames(it.genre_ids)
            it.toMedia()
        }
    }

    override suspend fun getSeries(categoryId: String, page: Int): List<Media> {
        return moviesApiService.getSeries(categoryId, apiKey, page).results.map {
            it.toMedia()
        }
    }

    override suspend fun getCastList(categoryId: String, mediaId: Int): List<CastMember> {
        return moviesApiService.getCastList(categoryId, mediaId.toString(), apiKey).cast.map {
            it.toCastMember()
        }
    }

    override suspend fun getGenreList(mediaType: String): List<MediaGenre> {
        return moviesApiService.getGenreList(mediaType, apiKey).genres.map {
            it.toMediaGenre()
        }
    }

    override suspend fun getFavorites(): List<Media> {
        return movieDAO.getAllMedia()
    }

    override suspend fun getFavoriteMedia(mediaId: Int): Media {
        return movieDAO.getMedia(mediaId)
    }

    override suspend fun isMediaInFavorites(mediaId: Int): Int {
        return movieDAO.isMediaInFavorites(mediaId)
    }

    override suspend fun addToFavorites(media: Media) {
        movieDAO.insertMedia(media)
    }

    override suspend fun removeFromFavorites(media: Media) {
        movieDAO.removeMedia(media)
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

    private fun SerieResultDTO.toMedia(): Media =
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

    private fun toReleaseYear(releaseDate: String): Int {
        return releaseDate.take(4).toInt()
    }

    private fun GenreDTO.toMediaGenre() =
        MediaGenre(
            id = id,
            name = name
        )

    private fun CastDTO.toCastMember(): CastMember =
        CastMember(
            actorImage = mediaSmallPosterBaseUrl + profile_path,
            actorName = name,
            characterName = character,
        )

}

