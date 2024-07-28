package com.caracrepair.app.presentation.mycar.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.CarResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCarItem(
    val id: String,
    val carName: String,
    val carLicenseNumber: String,
    val carYear: String
) : Parcelable {
    constructor(data: CarResponse?) : this(
        id = data?.id.orEmpty(),
        carName = data?.name.orEmpty(),
        carLicenseNumber = data?.licenseNumber.orEmpty(),
        carYear = data?.carYear.orEmpty()
    )
}