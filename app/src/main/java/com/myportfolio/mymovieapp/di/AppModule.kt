package com.myportfolio.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.myportfolio.mymovieapp.data.MovieRepository
import com.myportfolio.mymovieapp.data.MovieRepositoryImpl
import com.myportfolio.mymovieapp.data.MoviesApiService
import com.myportfolio.mymovieapp.data.database.MovieDAO
import com.myportfolio.mymovieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// A Hilt Module used to provide instances of retrofit, room database, the data access object and the repository.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Contains the API URL (Uniform Resource Locator).
    private const val baseUrl = "https://api.themoviedb.org/3/"

    // Provides the API service instance.
    @Provides
    @Singleton
    fun providesApiService(): MoviesApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }

    // Provides the Room database instance.
    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    // Provides the Data Access Object instance.
    @Provides
    @Singleton
    fun providesMovieDAO(movieDatabase: MovieDatabase): MovieDAO {
        return movieDatabase.getMovieDAO()
    }

    // Provides the repository instance.
    @Provides
    @Singleton
    fun providesMovieRepository(moviesApiService: MoviesApiService, movieDAO: MovieDAO): MovieRepository {
        return MovieRepositoryImpl(moviesApiService, movieDAO)
    }
}