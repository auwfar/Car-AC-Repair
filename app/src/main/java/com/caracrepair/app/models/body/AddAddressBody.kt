package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class AddAddressBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("title")
    val label: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("description")
    val addressNote: String,
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("long")
    val long: String?
)