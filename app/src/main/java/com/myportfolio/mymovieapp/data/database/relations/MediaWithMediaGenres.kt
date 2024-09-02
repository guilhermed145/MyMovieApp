package com.myportfolio.mymovieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.myportfolio.mymovieapp.data.database.entities.MediaMediaGenreCrossRef
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

// A data class that models the relationship between the medias and the media's genres for the Room database.
data class MediaWithMediaGenres (
    @Embedded
    val media: Media,
    @Relation(
        parentColumn = "media_id",
        entityColumn = "genre_name",
        associateBy = Junction(MediaMediaGenreCrossRef::class)
    )
    val genres: List<MediaGenre>
)