package com.example.domain.usecase

import com.example.domain.models.UiState
import com.example.domain.repository.GiphyRepo
import kotlinx.coroutines.flow.Flow

class GetScrollStateUseCase(private val giphyRepo: GiphyRepo) {

    fun execute(): Flow<UiState> = giphyRepo.getScrollState()
}