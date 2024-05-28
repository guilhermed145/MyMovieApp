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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiService(): MoviesApiService {
        return Retrofit.Builder()
            .baseUrl("")
            .build()
            .create(MoviesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieDAO(movieDatabase: MovieDatabase): MovieDAO {
        return movieDatabase.getMovieDAO()
    }

    @Provides
    @Singleton
    fun providesMovieRepository(moviesApiService: MoviesApiService, movieDatabase: MovieDatabase): MovieRepository {
        return MovieRepositoryImpl(moviesApiService, movieDatabase)
    }

}