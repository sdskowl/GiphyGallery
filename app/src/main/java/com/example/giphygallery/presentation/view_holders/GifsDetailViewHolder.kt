package com.example.giphygallery.presentation.view_holders

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
import com.example.giphygallery.databinding.GifItemDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GifsDetailViewHolder(private val binding: GifItemDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private var currentGif: GifDomain? = null
    private var errorLoad = false
    private var countTry = 0
    private val requestManager = Glide.with(binding.root)

    fun bind(gif: GifDomain?) {
        currentGif = gif
        gif?.let { g ->

            loadImage()
            binding.gifView.setOnClickListener {
                if (errorLoad) {
                    loadImage()
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
                val listener = object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<GifDrawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (countTry < 3) {
                            countTry++
                            CoroutineScope(Dispatchers.Main).launch {
                                loadImage()
                            }
                        } else {
                            errorLoad = true
                        }
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

                }
                val requestBuilder = requestManager.asGif()
                    .load(g.originalUrl)
                    .override(Target.SIZE_ORIGINAL)
                    .placeholder(circularProgressDrawable)
                    .fallback(R.drawable.tap)
                    .error(R.drawable.tap)
                    .listener(listener)
                requestBuilder.into(gifView)
            }

        }
    }

    companion object {
        fun create(binding: GifItemDetailBinding): GifsDetailViewHolder {
            return GifsDetailViewHolder(binding)
        }
    }
}