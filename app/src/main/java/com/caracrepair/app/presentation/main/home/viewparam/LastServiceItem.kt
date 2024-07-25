package com.caracrepair.app.presentation.main.home.viewparam

import com.caracrepair.app.models.response.HomePageResponse

data class LastServiceItem(
    val id: Int,
    val carName: String,
    val serviceDate: String,
    val distance: String
) {
    constructor(model: HomePageResponse.LastServiceModel) : this(
        model.orderId ?: 0,
        model.carName.orEmpty(),
        model.orderTime.orEmpty(),
        model.carDistance.orEmpty()
    )
}