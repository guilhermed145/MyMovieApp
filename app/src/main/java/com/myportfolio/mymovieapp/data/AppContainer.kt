package com.myportfolio.mymovieapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Container {
    val appRepository: MovieRepository
}

class AppContainer: Container {
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for making api calls.
     */
    private val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }

    /**
     * Dependency Injection implementation for the app repository.
     */
    override val appRepository: MovieRepository by lazy {
        MovieRepositoryImpl(retrofitService)
    }
}