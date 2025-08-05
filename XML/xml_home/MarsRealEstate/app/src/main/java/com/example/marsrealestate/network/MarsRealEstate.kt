package com.example.marsrealestate.network

import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import android.os.Parcelable

/**
 * Represents a single real estate item on Mars.
 * This data class is designed to be serializable for network communication
 * and parcelable for passing between Android components.
 *
 * @property id The unique identifier for the real estate item.
 * @property imgSrc The URL of the image for the real estate item. Mapped from "img_src" in JSON.
 * @property price The price of the real estate item.
 * @property type The type of real estate (e.g., "rent", "buy").
 */
@Serializable
@Parcelize
data class MarsRealEstateItem(
    val id: String,
    @SerialName("img_src") val imgSrc: String,
    val price: Int,
    val type: String
) : Parcelable {
    val isRental
        get() = type == "rent"
}
