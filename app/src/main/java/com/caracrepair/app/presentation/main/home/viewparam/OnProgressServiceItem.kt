package com.caracrepair.app.presentation.main.home.viewparam

import com.caracrepair.app.models.response.HomePageResponse

data class OnProgressServiceItem(
    val id: String,
    val carName: String,
    val status: String
) {
    constructor(model: HomePageResponse.OnProgressServiceModel) : this(
        model.orderId.orEmpty(),
        model.carName.orEmpty(),
        model.status.orEmpty()
    )
}