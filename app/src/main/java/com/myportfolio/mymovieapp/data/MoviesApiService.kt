package com.myportfolio.mymovieapp.data

import com.myportfolio.mymovieapp.data.dto.CastListDTO
import com.myportfolio.mymovieapp.data.dto.GenreListDTO
import com.myportfolio.mymovieapp.data.dto.MovieDetailResultDTO
import com.myportfolio.mymovieapp.data.dto.MovieListDTO
import com.myportfolio.mymovieapp.data.dto.SeriesDetailResultDTO
import com.myportfolio.mymovieapp.data.dto.SeriesListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Main API Service for the app. Retrieves all the data necessary from the TMDB API.
interface MoviesApiService {
    // Retrieves a limited list of movies from a specific category (popular, top rated or upcoming).
    @GET("movie/{category}?")
    suspend fun getMoviesInCategory(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): MovieListDTO

    // Retrieves a limited list of tv series from a specific category (popular or top rated).
    @GET("tv/{category}?")
    suspend fun getSeriesInCategory(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): SeriesListDTO

    // Retrieves details about a specific movie given the id.
    @GET("movie/{mediaId}?")
    suspend fun getMovie(
        @Path("mediaId") mediaId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): MovieDetailResultDTO

    // Retrieves details about a specific tv series given the id.
    @GET("tv/{mediaId}?")
    suspend fun getSeries(
        @Path("mediaId") mediaId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): SeriesDetailResultDTO

    // Retrieves a list with all cast members from a movie or tv series.
    @GET("{category_id}/{media_id}/credits?")
    suspend fun getCastList(
        @Path("category_id") categoryId: String,
        @Path("media_id") seriesId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): CastListDTO

    // Retrieves a list that contains all movie or tv series genres and their respective ids.
    @GET("genre/{media_type}/list?")
    suspend fun getGenreList(
        @Path("media_type") mediaType: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): GenreListDTO
}