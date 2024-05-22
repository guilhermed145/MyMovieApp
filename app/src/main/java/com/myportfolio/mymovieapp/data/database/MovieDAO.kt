package com.myportfolio.mymovieapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myportfolio.mymovieapp.model.Media

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: Media)

    @Delete
    suspend fun removeMedia(media: Media)

    @Query("SELECT * FROM medias")
    suspend fun selectAllMedia(): List<Media>

}