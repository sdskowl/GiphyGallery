package com.example.giphygallery.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.UiModel
import com.example.giphygallery.R
import com.example.giphygallery.databinding.EmptyItemBinding
import com.example.giphygallery.databinding.GifItemDetailBinding
import com.example.giphygallery.presentation.view_holders.EmptyHolder
import com.example.giphygallery.presentation.view_holders.GifsDetailViewHolder

class GifsDetailAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(GifsAdapter.UIMODEL_COMPARATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when {
                uiModel is UiModel.RepoItem -> {
                    (holder as GifsDetailViewHolder).bind(uiModel.gif)
                }
                else -> {
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.gif_item_detail -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding: GifItemDetailBinding =
                    GifItemDetailBinding.inflate(inflater, parent, false)
                GifsDetailViewHolder.create(binding)
            }
            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding: EmptyItemBinding = EmptyItemBinding.inflate(inflater, parent, false)
                EmptyHolder.create(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when {
            item is UiModel.RepoItem -> R.layout.gif_item_detail
            else -> R.layout.empty_item
        }
    }
}