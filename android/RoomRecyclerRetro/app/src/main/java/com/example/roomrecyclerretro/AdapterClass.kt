package com.example.roomrecyclerretro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomrecyclerretro.database.Countries
import com.example.roomrecyclerretro.networklayer.CountriesItem

class AdapterClass(private val dataList: List<Countries?>): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]

        val url: String = currentItem?.flag!!.toString()
//        val url: String = currentItem?.flags?.png!!
        // Load the image into the ImageView using Glide
        Glide.with(holder.itemView.context)
            .load(url)
            .into(holder.rvImage);
        holder.rvTitle.text = currentItem?.name.toString()
//        holder.rvTitle.text = currentItem?.name?.common
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.imageView)
        val rvTitle: TextView = itemView.findViewById(R.id.textView_title)
    }
}