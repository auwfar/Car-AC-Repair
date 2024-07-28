package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val label: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("description")
    val addressNote: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?
)