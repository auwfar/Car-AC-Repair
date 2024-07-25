package com.caracrepair.app.models.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.LocationResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val lat: Double,
    val long: Double
) : Parcelable {
    constructor(location: LocationResponse?) : this(
        location?.lat ?: 0.0,
        location?.long ?: 0.0
    )
}