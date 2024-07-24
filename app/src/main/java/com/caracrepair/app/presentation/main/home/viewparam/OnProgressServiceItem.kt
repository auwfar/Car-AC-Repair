package com.caracrepair.app.presentation.main.home.viewparam

import com.caracrepair.app.models.responsemodel.HomePageResponse

data class OnProgressServiceItem(
    val id: Int,
    val carName: String,
    val status: String
) {
    constructor(model: HomePageResponse.OnProgressServiceModel) : this(
        model.orderId?.toInt() ?: 0,
        model.carName.orEmpty(),
        model.status.orEmpty()
    )
}