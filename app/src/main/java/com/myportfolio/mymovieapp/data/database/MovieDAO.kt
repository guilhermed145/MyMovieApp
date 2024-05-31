package com.myportfolio.mymovieapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.myportfolio.mymovieapp.data.database.entities.MediaMediaGenreCrossRef
import com.myportfolio.mymovieapp.data.database.relations.MediaGenreWithMedias
import com.myportfolio.mymovieapp.data.database.relations.MediaWithCastMembers
import com.myportfolio.mymovieapp.data.database.relations.MediaWithMediaGenres
import com.myportfolio.mymovieapp.model.CastMember
import com.myportfolio.mymovieapp.model.Media
import com.myportfolio.mymovieapp.model.MediaGenre

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: Media)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastMember(castMember: CastMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaGenre(mediaGenre: MediaGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaMediaGenreCrossRef(crossRef: MediaMediaGenreCrossRef)

    @Delete
    suspend fun removeMedia(media: Media)

    @Query("SELECT EXISTS(SELECT 1 FROM medias WHERE media_id = :mediaId)")
    suspend fun isMediaInFavorites(mediaId: Int): Int

    @Query("SELECT * FROM medias")
    suspend fun getAllMedia(): List<Media>

    @Query("SELECT * FROM medias WHERE media_id = :mediaId LIMIT 1")
    suspend fun getMedia(mediaId: Int): Media

    @Transaction
    @Query("SELECT * FROM medias WHERE media_id = :mediaId")
    suspend fun getMediaWithCastMembers(mediaId: Int): List<MediaWithCastMembers>

    @Transaction
    @Query("SELECT * FROM media_genres WHERE genre_name = :genreName")
    suspend fun getMediasOfMediaGenre(genreName: String): List<MediaGenreWithMedias>

    @Transaction
    @Query("SELECT * FROM medias WHERE media_id = :mediaId")
    suspend fun getMediaGenresOfMedia(mediaId: String): List<MediaWithMediaGenres>

}