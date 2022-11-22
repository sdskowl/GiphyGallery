package com.example.giphygallery.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.UiModel
import com.example.giphygallery.databinding.GifItemDetailBinding
import com.example.giphygallery.presentation.view_holders.GifsDetailViewHolder

class GifsDetailAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(GifsAdapter.UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (it) {
                is UiModel.RepoItem -> {
                    val holderView = (holder as GifsDetailViewHolder)
                    holderView.bind(it.gif)
                }
                else -> {
                }
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val holderView = (holder as GifsDetailViewHolder)
        holderView.cancelScope()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: GifItemDetailBinding =
            GifItemDetailBinding.inflate(inflater, parent, false)
        return GifsDetailViewHolder.create(binding)

    }

}