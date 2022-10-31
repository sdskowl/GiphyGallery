package com.example.domain.models

sealed class UiModel {
    data class RepoItem(val gif: GifDomain) : UiModel()
}
