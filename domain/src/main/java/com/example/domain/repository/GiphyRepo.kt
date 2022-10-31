package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.GifDomain
import com.example.domain.models.UiAction
import com.example.domain.models.UiModel
import com.example.domain.models.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GiphyRepo {
    fun searchGifs(query: String): Flow<PagingData<GifDomain>>
    fun getListGifs(): Flow<PagingData<UiModel>>
    fun startAction(action: UiAction)
    fun getScrollState(): Flow<UiState>
    suspend fun hideGif(gif: GifDomain)
}