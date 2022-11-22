package com.example.domain.usecase

import com.example.domain.models.GifDomain
import com.example.domain.repository.GiphyRepo

class HideGifUseCase(private val giphyRepo: GiphyRepo) {
    suspend fun execute(gifDomain: GifDomain) {
        giphyRepo.hideGif(gif = gifDomain)
    }
}