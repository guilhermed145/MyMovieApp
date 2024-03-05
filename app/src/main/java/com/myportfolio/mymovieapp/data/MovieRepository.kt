package com.myportfolio.mymovieapp.data

import com.myportfolio.mymovieapp.BuildConfig
import com.myportfolio.mymovieapp.data.dto.CastDTO
import com.myportfolio.mymovieapp.data.dto.GenreDTO
import com.myportfolio.mymovieapp.data.dto.MovieResultDTO
import com.myportfolio.mymovieapp.data.dto.SerieResultDTO
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

/**
 * The Repository object for the movies data.
 */
interface MovieRepository {
    suspend fun getPopularMovies(): List<Media>
    suspend fun getTopRatedMovies(): List<Media>
    suspend fun getUpcomingMovies(): List<Media>
    suspend fun getPopularSeries(): List<Media>
    suspend fun getTopRatedSeries(): List<Media>
    suspend fun getMovieCast(movieId: Int): List<CastMember>
    suspend fun getSeriesCast(seriesId: Int): List<CastMember>
    suspend fun getGenreList(mediaType: String): List<MediaGenre>
}

class MovieRepositoryImpl(private val moviesApiService: MoviesApiService) : MovieRepository {

    private val mediaBigPosterBaseUrl = "https://image.tmdb.org/t/p/original/"
    private val mediaSmallPosterBaseUrl = "https://image.tmdb.org/t/p/w92/"
    private val apiKey = BuildConfig.API_KEY

    override suspend fun getPopularMovies(): List<Media> {
        return moviesApiService.getMovies("popular", apiKey).results.map {
            it.toMedia()
        }
    }

    override suspend fun getTopRatedMovies(): List<Media> {
        return moviesApiService.getMovies("top_rated", apiKey).results.map {
            it.toMedia()
        }
    }

    override suspend fun getUpcomingMovies(): List<Media> {
        return moviesApiService.getMovies("upcoming", apiKey).results.map {
            it.toMedia()
        }
    }

    override suspend fun getPopularSeries(): List<Media> {
        return moviesApiService.getSeries("popular", apiKey).results.map {
            it.toMedia()
        }
    }

    override suspend fun getTopRatedSeries(): List<Media> {
        return moviesApiService.getSeries("top_rated", apiKey).results.map {
            it.toMedia()
        }
    }

    override suspend fun getMovieCast(movieId: Int): List<CastMember> {
        return moviesApiService.getMovieCast(movieId.toString(), apiKey).cast.map {
            it.toCastMember()
        }
    }

    override suspend fun getSeriesCast(seriesId: Int): List<CastMember> {
        return moviesApiService.getSeriesCast(seriesId.toString(), apiKey).cast.map {
            it.toCastMember()
        }
    }

    override suspend fun getGenreList(mediaType: String): List<MediaGenre> {
        return moviesApiService.getGenreList(mediaType, apiKey).genres.map {
            it.toMediaGenre()
        }
    }

    private fun MovieResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = title,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(release_date),
            synopsis = overview,
            genreIdList = genre_ids,
        )

    private fun SerieResultDTO.toMedia(): Media =
        Media(
            id = id,
            mediaName = name,
            bigPoster = mediaBigPosterBaseUrl + poster_path,
            smallPoster = mediaSmallPosterBaseUrl + poster_path,
            releaseYear = toReleaseYear(first_air_date),
            synopsis = overview,
            genreIdList = genre_ids,
        )

    private fun toReleaseYear(releaseDate: String): Int {
        return releaseDate.take(4).toInt()
    }

    private fun CastDTO.toCastMember(): CastMember =
        CastMember(
            actorImage = mediaSmallPosterBaseUrl + profile_path,
            actorName = name,
            characterName = character,
        )
    private fun GenreDTO.toMediaGenre() =
        MediaGenre(
            id = id,
            name = name
        )


}

