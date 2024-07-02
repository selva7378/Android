package com.example.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Info (

  @SerialName("seed"    ) var seed    : String? = null,
  @SerialName("results" ) var results : Int?    = null,
  @SerialName("page"    ) var page    : Int?    = null,
  @SerialName("version" ) var version : String? = null

)