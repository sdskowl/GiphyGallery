package com.example.giphygallery.presentation.view_holders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.domain.models.GifDomain
import com.example.giphygallery.R
import com.example.giphygallery.databinding.GifItemBinding

class GifsViewHolder(
    private val binding: GifItemBinding,
    hideGif: (gif: GifDomain) -> Unit,
    gifClick: (position: Int) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private val TAG: String = "GifsViewHolder"
    private var currentGif: GifDomain? = null
    private var pos: Int? = null
    private var errorLoad = false

    init {
        binding.hideButton.setOnClickListener {
            currentGif?.let { gif ->
                Log.d(TAG, "hide button has been pressed")
                hideGif.invoke(gif)
            }
        }
        binding.gifView.setOnClickListener {
            pos?.let {
                if (!errorLoad) {
                    gifClick.invoke(it)
                } else {
                    currentGif?.let {
                        loadImage()
                    }
                }
            }
        }
    }

    fun bind(gif: GifDomain?, position: Int) {
        currentGif = gif
        pos = position
        gif?.let { g ->
            with(binding) {
                if (!g.hide) {
                    loadImage()
                    textView.text = position.toString()
                    Log.d(TAG, "START BINDING ${g.smallUrl}")
                } else {
                    hide()
                }
            }
        }
    }

    private fun loadImage() {
        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        with(binding) {
            currentGif?.let { g ->
                Glide.with(root).asGif()
                    .load(g.smallUrl)
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.tap)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .centerCrop()
                    .fitCenter()
                    .listener(object : RequestListener<GifDrawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<GifDrawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            errorLoad = true
                            Log.d(TAG, "Load failed")
                            return false
                        }

                        override fun onResourceReady(
                            resource: GifDrawable?,
                            model: Any?,
                            target: Target<GifDrawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            errorLoad = false
                            return false
                        }

                    })
                    .into(gifView)
            }

        }
    }

    private fun hide() {
        itemView.visibility = View.GONE
        itemView.layoutParams.height = 0
    }

    companion object {
        fun create(
            binding: GifItemBinding,
            hideGif: (gif: GifDomain) -> Unit,
            gifClick: (position: Int) -> Unit
        ): GifsViewHolder {
            return GifsViewHolder(binding, hideGif, gifClick)
        }
    }
}