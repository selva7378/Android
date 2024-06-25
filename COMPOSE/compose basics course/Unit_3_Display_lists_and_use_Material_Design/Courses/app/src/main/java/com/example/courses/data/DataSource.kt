package com.example.courses.data

import com.example.courses.R
import com.example.courses.model.CourseCategory

object DataSource {
    val topics = listOf(
        CourseCategory(R.string.architecture, 58, R.drawable.architecture),
        CourseCategory(R.string.crafts, 121, R.drawable.crafts),
        CourseCategory(R.string.business, 78, R.drawable.business),
        CourseCategory(R.string.culinary, 118, R.drawable.culinary),
        CourseCategory(R.string.design, 423, R.drawable.design),
        CourseCategory(R.string.fashion, 92, R.drawable.fashion),
        CourseCategory(R.string.film, 165, R.drawable.film),
        CourseCategory(R.string.gaming, 164, R.drawable.gaming),
        CourseCategory(R.string.drawing, 326, R.drawable.drawing),
        CourseCategory(R.string.lifestyle, 305, R.drawable.lifestyle),
        CourseCategory(R.string.music, 212, R.drawable.music),
        CourseCategory(R.string.painting, 172, R.drawable.painting),
        CourseCategory(R.string.photography, 321, R.drawable.photography),
        CourseCategory(R.string.tech, 118, R.drawable.tech)
    )
}