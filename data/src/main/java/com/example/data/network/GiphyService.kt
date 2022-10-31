package com.example.data.network

import com.example.data.BuildConfig
import com.example.data.network.models.GiphyData
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("search")
    suspend fun searchGifs(
        @Query("api_key", encoded = true) api: String = API_KEY,
        @Query("q", encoded = true) q: String,
        @Query("limit", encoded = true) limit: Int,
        @Query("offset", encoded = true) offset: Int,
        @Query("rating", encoded = true) rating: String = "g",
        @Query("lang", encoded = true) lang: String = "en"
    ): NetworkResponse<GiphyData, String>

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}