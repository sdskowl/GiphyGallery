package com.example.data.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.storage.room.models.Gif
import com.example.data.storage.room.models.GifHide
import com.example.data.storage.room.models.RemoteKeys

@Database(
    entities = [Gif::class, RemoteKeys::class, GifHide::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}