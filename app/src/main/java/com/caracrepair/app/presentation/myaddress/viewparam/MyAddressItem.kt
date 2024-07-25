package com.caracrepair.app.presentation.myaddress.viewparam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyAddressItem(
    val id: Int,
    val addressLabel: String,
    val address: String
) : Parcelable