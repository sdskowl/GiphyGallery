package com.example.domain.usecase

import com.example.domain.models.UiAction
import com.example.domain.repository.GiphyRepo

class StartActionUseCase(private val giphyRepo: GiphyRepo) {

    fun execute(action: UiAction){
        giphyRepo.startAction(action)
    }
}