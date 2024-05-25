package com.myportfolio.mymovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast_members")
data class CastMember (
    @PrimaryKey
    @ColumnInfo(name = "actor_name")
    var actorName: String,
    @ColumnInfo(name = "actor_image")
    var actorImage: String,
    @ColumnInfo(name = "character_name")
    var characterName: String,
    @ColumnInfo(name = "media_id")
    var mediaId: Int = 0,
)