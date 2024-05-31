package com.myportfolio.mymovieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.myportfolio.mymovieapp.data.database.entities.MediaMediaGenreCrossRef
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

data class MediaGenreWithMedias (
    @Embedded
    val genre: MediaGenre,
    @Relation(
        parentColumn = "genre_name",
        entityColumn = "media_id",
        associateBy = Junction(MediaMediaGenreCrossRef::class)
    )
    val medias: List<Media>
)