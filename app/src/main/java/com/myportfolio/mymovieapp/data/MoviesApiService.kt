package com.myportfolio.mymovieapp.data

import com.myportfolio.mymovieapp.data.dto.CastListDTO
import com.myportfolio.mymovieapp.data.dto.GenreListDTO
import com.myportfolio.mymovieapp.data.dto.MovieListDTO
import com.myportfolio.mymovieapp.data.dto.SerieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Main API Service for the app. Retrieves all the data necessary from the weather API.
 */
interface MoviesApiService {
    @GET("movie/{category}?")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): MovieListDTO

    @GET("tv/{category}?")
    suspend fun getSeries(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): SerieListDTO

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