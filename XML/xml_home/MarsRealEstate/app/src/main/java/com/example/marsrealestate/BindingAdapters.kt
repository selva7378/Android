package com.example.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.network.MarsRealEstateItem
import com.example.marsrealestate.overview.MarsApiStatus
import com.example.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // Convert the URL string to a Uri, ensuring it uses HTTPS
        // This is a good practice for security and to avoid cleartext traffic issues on newer Android versions.
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()

        // Use Glide to load the image
        Glide.with(imgView.context)
            .load(imgUri) // Load the image from the constructed URI
            // Apply request options for placeholders and error images
            .apply(
                RequestOptions()
                    // Set a drawable to display while the image is loading
                    // Replace R.drawable.loading_animation with your actual loading drawable
                    .placeholder(R.drawable.loading_animation)
                    // Set a drawable to display if the image loading fails
                    // Replace R.drawable.broken_image with your actual error drawable
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView) // Load the image into the ImageView
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsRealEstateItem>?) {
    val adapter = recyclerView.adapter as? PhotoGridAdapter
    adapter?.submitList(data)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}
