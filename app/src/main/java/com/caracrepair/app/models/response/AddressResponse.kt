package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("address_note")
    val addressNote: String?,
    @SerializedName("location")
    val location: LocationResponse?
)