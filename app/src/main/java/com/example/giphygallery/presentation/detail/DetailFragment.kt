package com.example.giphygallery.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.viewpager2.widget.ViewPager2
import com.example.domain.models.UiModel
import com.example.giphygallery.R
import com.example.giphygallery.databinding.FragmentDetailBinding
import com.example.giphygallery.presentation.adapters.GifsDetailAdapter
import com.example.giphygallery.presentation.adapters.GifsLoadStateAdapter
import com.example.giphygallery.presentation.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "DETAIL_FRAGMENT"

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val binding: FragmentDetailBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentDetailBinding.inflate(layoutInflater)
    }
    private val vm: SharedViewModel by hiltNavGraphViewModels(R.id.navigation)
    private val args: DetailFragmentArgs by navArgs()
    private var firstStart = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindState(
            pagingData = vm.pagingDataFlow
        )

    }

    private fun FragmentDetailBinding.bindState(
        pagingData: Flow<PagingData<UiModel>>
    ) {
        val gifsAdapter = GifsDetailAdapter()
        val footer = GifsLoadStateAdapter { gifsAdapter.retry() }
        binding.listGifs.apply {
            adapter = gifsAdapter.withLoadStateFooter(footer)
            offscreenPageLimit = 5
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        bindList(
            gifsAdapter = gifsAdapter,
            pagingData = pagingData,
        )
    }

    private fun FragmentDetailBinding.bindList(
        gifsAdapter: GifsDetailAdapter,
        pagingData: Flow<PagingData<UiModel>>
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingData.collectLatest {
                    gifsAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                gifsAdapter.loadStateFlow.collect { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && loadState.append is LoadState.NotLoading && gifsAdapter.itemCount == 0 && loadState.prepend is LoadState.NotLoading
                    if (!isListEmpty) {
                        if (firstStart) {
                            binding.listGifs.setCurrentItem(args.position, false)
                            firstStart = false
                        }
                    }
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
        }
    }

}

