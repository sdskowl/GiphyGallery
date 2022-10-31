@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.giphygallery.presentation.viewmodels

import android.util.Log
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getListGifsUseCase: GetListGifsUseCase,
    private val startActionUseCase: StartActionUseCase,
    private val getScrollStateUseCase: GetScrollStateUseCase,
    private val hideGifUseCase: HideGifUseCase
) : ViewModel() {
    val pagingDataFlow: Flow<PagingData<UiModel>> =
        getListGifsUseCase.execute().cachedIn(viewModelScope)
    val accept: (UiAction) -> Unit = { uiAction: UiAction -> startActionUseCase.execute(uiAction) }
    val state: StateFlow<UiState> = getScrollStateUseCase.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = UiState()
    )

    override fun onCleared() {
        Log.d("VMCLEAR", "onCleared")
        super.onCleared()
    }

    fun hideGif(gif: GifDomain) {
        hideGifUseCase.execute(gif)
    }
}
