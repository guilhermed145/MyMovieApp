package com.myportfolio.mymovieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.myportfolio.mymovieapp.util.Constants
import java.io.Serializable

// Model class for movies and tv series, containing it's most important information.
@Entity(tableName = "medias")
data class Media(
    @PrimaryKey
    @ColumnInfo(name = "media_id")
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
    @ColumnInfo(name = "media_type")
    var mediaType: String = Constants.movieTypeId,
    @Ignore
    var genreIdList: List<Int> = listOf(),
    @Ignore
    var genreList: List<MediaGenre> = listOf(),
    @Ignore
    var castList: List<CastMember> = listOf(),
) : Serializable