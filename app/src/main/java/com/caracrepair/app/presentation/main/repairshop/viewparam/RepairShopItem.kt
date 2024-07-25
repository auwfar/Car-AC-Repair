package com.caracrepair.app.presentation.main.repairshop.viewparam

import com.caracrepair.app.models.response.RepairShopResponse

data class RepairShopItem(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val image: String
) {
    constructor(response: RepairShopResponse) : this(
        response.id ?: 0,
        response.name.orEmpty(),
        response.address.orEmpty(),
        response.adminPhoneNumber.orEmpty(),
        response.imageUrl.orEmpty()
    )
}