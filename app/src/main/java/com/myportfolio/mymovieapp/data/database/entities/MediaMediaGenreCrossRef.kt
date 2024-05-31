package com.myportfolio.mymovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["media_id", "genre_name"])
data class MediaMediaGenreCrossRef (
    @ColumnInfo(name = "media_id")
    val mediaId: Int,
    @ColumnInfo(name = "genre_name")
    val genreName: String
)