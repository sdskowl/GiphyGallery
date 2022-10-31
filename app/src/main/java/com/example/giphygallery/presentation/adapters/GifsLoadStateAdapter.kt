/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.giphygallery.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.giphygallery.databinding.GifLoadStateFooterViewItemBinding
import com.example.giphygallery.presentation.view_holders.GifsLoadStateViewHolder

class GifsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<GifsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: GifsLoadStateViewHolder, loadState: LoadState) {
        val layoutParams =
            holder.itemView.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = true
        } else {
            layoutParams.height = StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT
            layoutParams.width = StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT
        }
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GifsLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: GifLoadStateFooterViewItemBinding =
            GifLoadStateFooterViewItemBinding.inflate(inflater, parent, false)

        return GifsLoadStateViewHolder.create(binding, retry)
    }
}
