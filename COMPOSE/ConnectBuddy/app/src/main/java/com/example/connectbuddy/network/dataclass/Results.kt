package com.example.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Results (

  @SerialName("gender"  ) var gender  : String?  = null,
  @SerialName("name"    ) var name    : Name?    = Name(),
  @SerialName("email"   ) var email   : String?  = null,
  @SerialName("phone"   ) var phone   : String?  = null,
  @SerialName("cell"    ) var cell    : String?  = null,
  @SerialName("id"      ) var id      : Id?      = Id(),
  @SerialName("picture" ) var picture : Picture? = Picture()

)