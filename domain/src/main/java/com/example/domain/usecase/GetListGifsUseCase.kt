package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.models.UiModel
import com.example.domain.repository.GiphyRepo
import kotlinx.coroutines.flow.Flow

class GetListGifsUseCase(private val giphyRepo: GiphyRepo) {

    fun execute(): Flow<PagingData<UiModel>> {
        return giphyRepo.getListGifs()
    }
}