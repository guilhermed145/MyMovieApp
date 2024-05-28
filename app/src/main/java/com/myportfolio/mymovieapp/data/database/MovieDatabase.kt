package com.myportfolio.mymovieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

@Database(version = 1, entities = [Media::class, CastMember::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDAO(): MovieDAO

}