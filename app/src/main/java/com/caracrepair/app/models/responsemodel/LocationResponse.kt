package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?
)