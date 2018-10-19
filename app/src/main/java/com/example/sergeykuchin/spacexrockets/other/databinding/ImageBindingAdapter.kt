package com.example.sergeykuchin.spacexrockets.other.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class ImageBindingAdapter(private val picasso: Picasso) {

    @BindingAdapter("bind:imageUrl", "bind:imageTargetWidth", "bind:imageTargetHeight")
    fun loadImage(view: ImageView?, url: String?, targetWidth: Int, targetHeight: Int) {

        picasso.cancelRequest(view ?: return)

        picasso
            .load(url)
            .resize(targetWidth, targetHeight)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("bind:loadImageIf", "bind:loadingImage")
    fun loadImageIf(view: ImageView?, isActive: Boolean, drawable: Drawable) {

        if (isActive) {
            view?.setImageDrawable(drawable)
        }
    }
}