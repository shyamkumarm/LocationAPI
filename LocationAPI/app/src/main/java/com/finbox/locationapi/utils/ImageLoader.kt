package com.finbox.locationapi.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import java.util.*
import kotlin.random.Random

object ImageLoader {


    fun loadImage(
        imageUrl: String?,
        imageViewToLoad: ImageView,
        placeHolderId: Int = 0
    ) {
        Glide.with(imageViewToLoad.context).load(imageUrl).apply {
            placeholder(placeHolderId)
            transform(CenterCrop())
            transition(DrawableTransitionOptions.withCrossFade())

        }.into(imageViewToLoad)

    }


}
