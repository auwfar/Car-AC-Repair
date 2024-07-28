package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class AddAddressBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("address_note")
    val addressNote: String,
    @SerializedName("location")
    val location: LocationBody?
)