package com.example.giphygallery.presentation.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.models.UiModel
import com.example.domain.models.UiState
import com.example.giphygallery.R
import com.example.giphygallery.databinding.FragmentMainBinding
import com.example.giphygallery.presentation.adapters.GifsAdapter
import com.example.giphygallery.presentation.adapters.GifsLoadStateAdapter
import com.example.giphygallery.presentation.utils.RemotePresentationState
import com.example.giphygallery.presentation.utils.asRemotePresentationState
import com.example.giphygallery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val vm: SharedViewModel by hiltNavGraphViewModels(R.id.navigation)
    private val binding: FragmentMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindState(
            uiState = vm.state,
            pagingData = vm.pagingDataFlow
        )

    }

    private fun FragmentMainBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>
    ) {
        val gifsAdapter =
            GifsAdapter({ gif -> vm.hideGif(gif) }, { position -> gifClick(position) })
        val header = GifsLoadStateAdapter { gifsAdapter.retry() }
        val footer = GifsLoadStateAdapter { gifsAdapter.retry() }
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        listGifs.layoutManager = manager
        listGifs.adapter = gifsAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = footer
        )
        bindSearch(
            uiState = uiState
        )
        bindList(
            header = header,
            gifsAdapter = gifsAdapter,
            uiState = uiState,
            pagingData = pagingData
        )
    }

    private fun gifClick(position: Int) {
        vm.saveScroll()
        val directions = MainFragmentDirections.actionMainFragmentToDetailFragment(position)
        findNavController().navigate(directions)
    }

    private fun FragmentMainBinding.bindSearch(
        uiState: StateFlow<UiState>
    ) {
        searchGifs.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateGifsListFromInput()
                true
            } else {
                false
            }
        }
        searchGifs.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateGifsListFromInput()
                true
            } else {
                false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                uiState
                    .map { it.query }
                    .distinctUntilChanged()
                    .collect(searchGifs::setText)
            }
        }
    }

    private fun FragmentMainBinding.bindList(
        header: GifsLoadStateAdapter,
        gifsAdapter: GifsAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>
    ) {
        val notLoading = gifsAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        retryButton.setOnClickListener { gifsAdapter.retry() }
        listGifs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    vm.saveScroll()
                }
            }
        })



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    pagingData.collectLatest { data ->
                        gifsAdapter.submitData(data)
                    }
                }

                launch {
                    gifsAdapter.loadStateFlow.collect { loadState ->
                        header.loadState = loadState.mediator
                            ?.refresh
                            ?.takeIf { it is LoadState.Error && gifsAdapter.itemCount > 0 }
                            ?: loadState.prepend

                        val isListEmpty =
                            loadState.refresh is LoadState.NotLoading && gifsAdapter.itemCount == 0

                        emptyList.isVisible = isListEmpty
                        // Only show the list if refresh succeeds, either from the the local db or the remote.
                        listGifs.isVisible =
                            loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                        // Show loading spinner during initial load or refresh.
                        progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                        // Show the retry state if initial load or refresh fails.
                        retryButton.isVisible =
                            loadState.mediator?.refresh is LoadState.Error && gifsAdapter.itemCount == 0

                        val errorState = loadState.source.append as? LoadState.Error
                            ?: loadState.source.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.prepend as? LoadState.Error
                        errorState?.let {
                            Toast.makeText(
                                requireContext(),
                                "\uD83D\uDE28 Wooops ${it.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                //makes scroll up
                launch {
                    shouldScrollToTop.collect { shouldScroll ->
                        if (shouldScroll) {
                            listGifs.scrollTo(0, 0)
                        }
                    }
                }

            }
        }


    }

    /**
     * This fun we use to send new query to our repository
     * */
    private fun FragmentMainBinding.updateGifsListFromInput() {
        searchGifs.text?.trim()?.let {
            if (it.isNotEmpty()) {
                vm.query(it.toString())
            }
        }
    }

}

