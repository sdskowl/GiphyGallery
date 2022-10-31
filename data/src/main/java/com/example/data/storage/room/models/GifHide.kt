package com.example.data.storage.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_hide")
data class GifHide(
    @PrimaryKey
    val id: String
)
