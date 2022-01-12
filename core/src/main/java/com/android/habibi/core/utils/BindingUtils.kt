package com.android.habibi.core.utils

import android.widget.ImageView
import com.android.habibi.core.BuildConfig
import com.bumptech.glide.Glide

fun setImage(imageView: ImageView, source: String) =
    Glide.with(imageView)
        .load(BuildConfig.BASE_URL_IMAGE + source)
        .centerCrop()
        .into(imageView)