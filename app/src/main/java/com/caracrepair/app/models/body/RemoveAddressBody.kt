package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class RemoveAddressBody(
    @SerializedName("id")
    val id: Int
)