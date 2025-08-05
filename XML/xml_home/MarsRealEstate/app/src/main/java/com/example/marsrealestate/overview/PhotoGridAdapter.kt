/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.marsrealestate.databinding.GridViewItemBinding
import com.example.marsrealestate.network.MarsRealEstateItem

class PhotoGridAdapter(private val onClick: (MarsRealEstateItem) -> Unit): ListAdapter<MarsRealEstateItem, RecyclerView.ViewHolder>(PhotoGridDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (holder is ViewHolder) {
            holder.bind(item, onClick)
        }
    }

    class ViewHolder private constructor(val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MarsRealEstateItem, onClick: (MarsRealEstateItem) -> Unit) {
            binding.property = item
            binding.root.setOnClickListener { onClick(item) }
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup, onClick: (MarsRealEstateItem) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridViewItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PhotoGridDiffCallback : DiffUtil.ItemCallback<MarsRealEstateItem>() {
    override fun areItemsTheSame(
        oldItem: MarsRealEstateItem,
        newItem: MarsRealEstateItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MarsRealEstateItem,
        newItem: MarsRealEstateItem
    ): Boolean {
        return oldItem == newItem
    }
}
