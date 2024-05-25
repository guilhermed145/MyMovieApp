package com.myportfolio.mymovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.myportfolio.mymovieapp.util.Constants
import java.io.Serializable

@Entity(tableName = "medias")
data class Media(
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = "media_name")
    var mediaName: String = "",
    @ColumnInfo(name = "big_poster")
    var bigPoster: String = "",
    @ColumnInfo(name = "medium_poster")
    var mediumPoster: String = "",
    @ColumnInfo(name = "small_poster")
    var smallPoster: String = "",
    @ColumnInfo(name = "release_year")
    var releaseYear: Int = 0,
    var synopsis: String = "",
    @Ignore
    var genreIdList: List<Int> = listOf(),
    @ColumnInfo(name = "genre_list")
    var genreNameList: List<String> = listOf(),
    @ColumnInfo(name = "cast_list")
    @Relation(parentColumn = "id", entityColumn = "media_id")
    var castList: List<CastMember> = listOf(),
    @ColumnInfo(name = "media_type")
    var mediaType: String = Constants.movieTypeId
) : Serializable