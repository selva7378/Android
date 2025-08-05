package com.example.newsweather.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsweather.R
import com.example.newsweather.database.roomdb.News
import com.example.newsweather.screens.ListingscreenDirections
import com.squareup.picasso.Picasso

class NewsListingAdapterClass(private val navController: NavController):
    RecyclerView.Adapter<NewsListingAdapterClass.ViewHolderClass>() {
    private val dataList: MutableList<News?>? = mutableListOf()

    fun addData(newData: List<News?>?) {
        if (newData != null) {
            dataList?.addAll(newData)
        }
        notifyDataSetChanged() // Notify adapter that data set has changed
    }

    fun clearData() {
        dataList?.clear()
        notifyDataSetChanged() // Notify adapter that data set has changed
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList?.get(position)!!

        val url: String = currentItem?.imageUrl!!.toString()
        Log.i("adapter", url)

        // Load the image into the ImageView using Glide
        Picasso.get().load(url).into(holder.rvImage)
        holder.rvTitle.text = currentItem?.title.toString()

        holder.rvDescription.text = currentItem?.content.toString()

        // Set click listener
        holder.itemView.setOnClickListener {
            navController.navigate(ListingscreenDirections.actionListingscreen3ToWebView(currentItem?.readMoreUrl!!))
//            navController.navigate(Li.actionListingscreen3ToWebView(currentItem?.readMoreUrl!!))
        }
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.imageView_thumbnail)
        val rvTitle: TextView = itemView.findViewById(R.id.textView_title)
        val rvDescription: TextView = itemView.findViewById(R.id.textView_description)
    }
}