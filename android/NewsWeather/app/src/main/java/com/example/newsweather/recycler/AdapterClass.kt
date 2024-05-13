package com.example.newsweather.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsweather.R
import com.example.newsweather.screens.ListingscreenDirections
import com.example.newsweather.viewmodels.NewsViewModel


class AdapterClass(private val dataList: List<String>,
                   private val newsViewModel: NewsViewModel)
    : RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category, parent, false)
        return ViewHolderClass(itemView)
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]

        holder.rvTitle.text = currentItem

        // Set click listener
        holder.itemView.setOnClickListener {
            Log.i("categor adapter", "on click is pressed")
            newsViewModel.category = currentItem
            newsViewModel.currentPage = 0
            newsViewModel.searchFlag = false
            newsViewModel.dbRetrieveCategory(currentItem, "category")
        }
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.textView_titleCategory)
    }
}