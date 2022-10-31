package com.example.giphygallery.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.GifDomain
import com.example.domain.models.UiModel
import com.example.giphygallery.databinding.GifItemBinding
import com.example.giphygallery.presentation.view_holders.GifsViewHolder

class GifsAdapter(
    private val hideGif: (gif: GifDomain) -> Unit,
    private val gifClick: (position: Int) -> Unit
) :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UIMODEL_COMPARATOR) {
    private val TAG = "GifsAdapter"
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let { model ->
            when {
                model is UiModel.RepoItem -> {
                    val gifHolder = (holder as GifsViewHolder)
                    gifHolder.bind(model.gif, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: GifItemBinding = GifItemBinding.inflate(inflater, parent, false)
        return GifsViewHolder.create(binding, hideGif, gifClick)
    }

    object UIMODEL_COMPARATOR : DiffUtil.ItemCallback<UiModel>() {
        override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
            return (oldItem is UiModel.RepoItem && newItem is UiModel.RepoItem) && oldItem.gif.id == newItem.gif.id
        }

        override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
            return oldItem == newItem
        }

    }

}