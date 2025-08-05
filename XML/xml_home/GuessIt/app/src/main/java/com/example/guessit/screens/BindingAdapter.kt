package com.example.guessit.screens

// BindingAdapters.kt

import android.widget.TextView
import androidx.databinding.BindingAdapter
import android.text.format.DateUtils

@BindingAdapter("formattedTime")
fun setFormattedTime(view: TextView, time: Long?) {
    time?.let {
        view.text = DateUtils.formatElapsedTime(it)
    }
}
