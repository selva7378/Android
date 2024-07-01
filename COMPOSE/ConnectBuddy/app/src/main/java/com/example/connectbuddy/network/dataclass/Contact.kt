package com.example.connectbuddy.network.dataclass

import com.example.example.Info
import com.example.example.Results
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Contact (
    @SerialName("results" ) var results : ArrayList<Results> = arrayListOf(),
    @SerialName("info"    ) var info    : Info?     = Info()
)