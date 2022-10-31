package com.example.domain.models

data class GifDomain(
    val id: String,
    val originalUrl: String,
    val smallUrl: String,
    val searchWords: String,
    var hide: Boolean
)
