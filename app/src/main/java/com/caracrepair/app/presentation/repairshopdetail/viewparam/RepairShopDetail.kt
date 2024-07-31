package com.caracrepair.app.presentation.repairshopdetail.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.RepairShopDetailResponse
import com.caracrepair.app.models.viewparam.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairShopDetail(
    val id: String,
    val imageUrl: String,
    val name: String,
    val description: String,
    val address: String,
    val adminPhoneNumber: String,
    val location: Location
) : Parcelable {
    constructor(response: RepairShopDetailResponse?) : this(
        response?.id.orEmpty(),
        response?.imageUrl.orEmpty(),
        response?.name.orEmpty(),
        response?.description.orEmpty(),
        response?.address.orEmpty(),
        response?.adminPhoneNumber.orEmpty(),
        Location(response)
    )
}