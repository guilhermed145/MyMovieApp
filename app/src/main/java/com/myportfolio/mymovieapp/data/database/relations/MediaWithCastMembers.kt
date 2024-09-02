package com.myportfolio.mymovieapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media

// A data class that models the relationship between the medias and the media's cast members for the Room database.
data class MediaWithCastMembers (
    @Embedded
    val media: Media,
    @Relation(
        parentColumn = "media_id",
        entityColumn = "media_id"
    )
    val castMembers: List<CastMember>
)