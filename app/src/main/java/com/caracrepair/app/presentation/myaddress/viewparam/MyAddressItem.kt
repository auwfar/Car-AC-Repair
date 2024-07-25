package com.caracrepair.app.presentation.myaddress.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.viewparam.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyAddressItem(
    val id: Int,
    val addressLabel: String,
    val address: String,
    val addressNote: String,
    val location: Location
) : Parcelable {
    constructor(address: AddressResponse?) : this(
        address?.id ?: 0,
        address?.label.orEmpty(),
        address?.address.orEmpty(),
        address?.addressNote.orEmpty(),
        Location(address?.location)
    )
}