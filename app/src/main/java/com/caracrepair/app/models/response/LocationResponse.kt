package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?
)