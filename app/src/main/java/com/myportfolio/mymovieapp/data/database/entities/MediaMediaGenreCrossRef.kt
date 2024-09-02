package com.myportfolio.mymovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

// Represents the cross-reference table between the media entity and the media genre entity.
@Entity(primaryKeys = ["media_id", "genre_name"])
data class MediaMediaGenreCrossRef (
    @ColumnInfo(name = "media_id")
    val mediaId: Int,
    @ColumnInfo(name = "genre_name")
    val genreName: String
)