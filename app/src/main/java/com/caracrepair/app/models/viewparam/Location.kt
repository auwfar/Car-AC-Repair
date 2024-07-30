package com.caracrepair.app.models.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.response.LocationResponse
import com.caracrepair.app.models.response.RepairShopDetailResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val lat: Double,
    val long: Double
) : Parcelable {
    constructor(response: RepairShopDetailResponse?) : this(
        response?.lat ?: 0.0,
        response?.long ?: 0.0
    )
    constructor(response: AddressResponse?) : this(
        response?.lat ?: 0.0,
        response?.long ?: 0.0
    )
}