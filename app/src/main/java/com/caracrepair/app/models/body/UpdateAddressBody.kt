package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class UpdateAddressBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val label: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("address_note")
    val addressNote: String,
    @SerializedName("location")
    val location: LocationBody?
)