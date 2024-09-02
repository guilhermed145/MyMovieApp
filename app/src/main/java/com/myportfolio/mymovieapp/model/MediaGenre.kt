package com.myportfolio.mymovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Model class for movies and tv series genres.
@Entity(tableName = "media_genres")
data class MediaGenre (
    var id: Int = 0,
    @PrimaryKey
    @ColumnInfo(name = "genre_name")
    var name: String = ""
)