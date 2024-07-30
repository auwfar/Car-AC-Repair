package com.caracrepair.app.presentation.repairshopdetail.viewparam

import com.caracrepair.app.models.response.RepairShopDetailResponse
import com.caracrepair.app.models.viewparam.Location

data class RepairShopDetail(
    val id: String,
    val imageUrl: String,
    val name: String,
    val description: String,
    val address: String,
    val adminPhoneNumber: String,
    val location: Location
) {
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