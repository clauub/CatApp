package com.quess.catapp.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.mikhaellopez.circularimageview.CircularImageView
import com.quess.catapp.R

object BindingAdapter {


    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions().diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
            )
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:imageUrlCircular")
    fun bindImageCircular(imageView: CircularImageView, url: String?) {
        Glide.with(imageView.context)
            .setDefaultRequestOptions(
                RequestOptions().diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
            )
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:visible")
    fun setAnimatedVisibility(target: ProgressBar, isVisible: Boolean) {
        target.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("app:visibleNetwork")
    fun setNetworkVisibility(target: ConstraintLayout, isNetwork: Boolean) {
        target.visibility = if (isNetwork) View.VISIBLE else View.GONE
    }
}