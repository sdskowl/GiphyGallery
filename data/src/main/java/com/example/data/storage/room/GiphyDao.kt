package com.example.data.storage.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.storage.room.models.Gif
import com.example.data.storage.room.models.GifHide

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Gif>)

    @Query(
        "SELECT * FROM gif WHERE searchWords LIKE :queryString "
    )
    fun gifBySearchHistory(queryString: String): PagingSource<Int, Gif>

    @Query("SELECT * FROM gif WHERE id=:gifId")
    fun getGifById(gifId: String): Gif

    @Query("DELETE FROM gif ")
    suspend fun clearGifs()

    @Insert(entity = GifHide::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGifToHideList(gif: GifHide)

    @Query("SELECT * FROM gif_hide WHERE id=:gifId")
    suspend fun findHidden(gifId: String): GifHide?

    @Update
    suspend fun hideGifInCurrentData(gif: Gif)
}