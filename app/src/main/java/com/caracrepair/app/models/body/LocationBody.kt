package com.caracrepair.app.models.body

import com.caracrepair.app.models.viewparam.Location
import com.google.gson.annotations.SerializedName

data class LocationBody(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double
) {
    constructor(location: Location) : this(location.lat, location.long)
}