package com.caracrepair.app.models.viewparam

import com.caracrepair.app.models.response.LocationResponse

data class Location(
    val lat: Double,
    val long: Double
) {
    constructor(location: LocationResponse?) : this(
        location?.lat ?: 0.0,
        location?.long ?: 0.0
    )
}