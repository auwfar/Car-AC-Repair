package com.caracrepair.app.presentation.mycar.viewparam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCarItem(
    val id: Int,
    val carName: String,
    val carLicenseNumber: String
) : Parcelable