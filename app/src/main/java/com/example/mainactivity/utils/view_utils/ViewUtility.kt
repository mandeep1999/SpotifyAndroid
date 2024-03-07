package com.example.mainactivity.utils.view_utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mainactivity.search_module.data.models.dtos.Icon
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

object ViewUtility {


    private fun getShimmerDrawable(
        duration: Long,
        transparency: Float,
        highlightTransparency: Float,
        direction: Int
    ): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(duration)
            .setBaseAlpha(transparency)
            .setHighlightAlpha(highlightTransparency)
            .setDirection(direction)
            .setAutoStart(true)
            .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
        return shimmerDrawable
    }

    fun loadIconIntoImageView(icon: Icon, imageView: ImageView, context: Context) {
        val id =
            context.resources.getIdentifier(icon.drawable ?: "", "drawable", context.packageName)
        if (id > 0) {
            imageView.setImageResource(id)
        } else if (icon.url?.isNotEmpty() == true) {
            val shimmerDrawable: Drawable =
                getShimmerDrawable(1000, 0.8f, 0.7f, Shimmer.Direction.LEFT_TO_RIGHT)
            Glide.with(imageView).load(icon.url)
                .placeholder(shimmerDrawable)
                .into(imageView)
        }
    }
}