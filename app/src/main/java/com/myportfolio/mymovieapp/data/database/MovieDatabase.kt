package com.myportfolio.mymovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myportfolio.mymovieapp.data.database.entities.MediaMediaGenreCrossRef
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

// The database class provides the Data Access Object instance to the app.
@Database(
    version = 1,
    entities = [Media::class, CastMember::class, MediaGenre::class, MediaMediaGenreCrossRef::class],
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    // Function that Room will use to return the DAO instance.
    abstract fun getMovieDAO(): MovieDAO

}