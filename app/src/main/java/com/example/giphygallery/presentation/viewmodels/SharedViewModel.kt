package com.example.giphygallery.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.models.GifDomain
import com.example.domain.models.UiAction
import com.example.domain.models.UiModel
import com.example.domain.models.UiState
import com.example.domain.usecase.GetListGifsUseCase
import com.example.domain.usecase.GetScrollStateUseCase
import com.example.domain.usecase.HideGifUseCase
import com.example.domain.usecase.StartActionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    getListGifsUseCase: GetListGifsUseCase,
    private val startActionUseCase: StartActionUseCase,
    getScrollStateUseCase: GetScrollStateUseCase,
    private val hideGifUseCase: HideGifUseCase
) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<UiModel>> =
        getListGifsUseCase.execute().cachedIn(viewModelScope)
    val state: StateFlow<UiState> = getScrollStateUseCase.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = UiState()
    )

    fun hideGif(gif: GifDomain) {
        viewModelScope.launch {
            hideGifUseCase.execute(gif)
        }
    }

    fun query(query: String) {
        viewModelScope.launch {
            startActionUseCase.execute(UiAction.Search(query = query))
        }
    }

    fun saveScroll() {
        viewModelScope.launch {
            startActionUseCase.execute(UiAction.Scroll(state.value.query))
        }
    }
}
