package org.kl.smartbuy.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageFromUrl")
fun bindIconFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}