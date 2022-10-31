package com.example.domain.models

data class UiState(
    val query: String = "",
    val lastQueryScrolled: String = "",
    val hasNotScrolledForCurrentSearch: Boolean = false
)