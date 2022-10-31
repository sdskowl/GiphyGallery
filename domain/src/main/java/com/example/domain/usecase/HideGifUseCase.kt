package com.example.domain.usecase

import com.example.domain.models.GifDomain
import com.example.domain.repository.GiphyRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HideGifUseCase(private val giphyRepo: GiphyRepo) {
    fun execute(gifDomain: GifDomain){
        CoroutineScope(Dispatchers.IO).launch{
            giphyRepo.hideGif(gif = gifDomain)
        }
    }
}