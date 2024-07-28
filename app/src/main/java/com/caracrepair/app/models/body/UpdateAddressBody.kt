package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class UpdateAddressBody(
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