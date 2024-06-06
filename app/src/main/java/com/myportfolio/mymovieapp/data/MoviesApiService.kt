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

/**
 * Main API Service for the app. Retrieves all the data necessary from the weather API.
 */
interface MoviesApiService {
    @GET("movie/{category}?")
    suspend fun getMoviesInCategory(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): MovieListDTO

    @GET("tv/{category}?")
    suspend fun getSeriesInCategory(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): SeriesListDTO

    @GET("movie/{mediaId}?")
    suspend fun getMovie(
        @Path("mediaId") mediaId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): MovieDetailResultDTO

    @GET("tv/{mediaId}?")
    suspend fun getSeries(
        @Path("mediaId") mediaId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): SeriesDetailResultDTO

    @GET("{category_id}/{media_id}/credits?")
    suspend fun getCastList(
        @Path("category_id") categoryId: String,
        @Path("media_id") seriesId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): CastListDTO

    @GET("genre/{media_type}/list?")
    suspend fun getGenreList(
        @Path("media_type") mediaType: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): GenreListDTO

}