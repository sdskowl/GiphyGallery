package com.example.data.storage.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif")
data class Gif(
    @PrimaryKey val id: String,
    val originalUrl: String,
    val smallUrl: String,
    val searchWords: String,
    var hide:Boolean
)
