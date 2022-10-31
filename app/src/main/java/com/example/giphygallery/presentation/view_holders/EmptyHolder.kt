package com.example.giphygallery.presentation.view_holders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.giphygallery.databinding.EmptyItemBinding

class EmptyHolder(binding: EmptyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        hide()
        Log.d("EmptyHolder", "empty holder init")
    }

    private fun hide() {
        itemView.visibility = View.GONE
        itemView.layoutParams.height = 0
    }

    companion object {
        fun create(binding: EmptyItemBinding): EmptyHolder {
            return EmptyHolder(binding)
        }
    }
}