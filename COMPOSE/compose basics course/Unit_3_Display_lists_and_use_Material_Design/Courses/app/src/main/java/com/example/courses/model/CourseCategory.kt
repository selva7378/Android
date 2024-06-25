package com.example.courses.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CourseCategory(
    @StringRes val topic: Int,
    val noOfCourse: Int,
    @DrawableRes val image: Int
)
