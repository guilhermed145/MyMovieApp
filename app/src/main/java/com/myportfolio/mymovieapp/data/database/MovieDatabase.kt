package com.myportfolio.mymovieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

@Database(version = 1, entities = [Media::class, CastMember::class, MediaGenre::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDAO(): MovieDAO

    companion object {
        @Volatile
        var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build().also { instance = it }
            }
        }
    }

}