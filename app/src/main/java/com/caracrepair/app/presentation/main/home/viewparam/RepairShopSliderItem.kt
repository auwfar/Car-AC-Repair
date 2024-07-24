package com.caracrepair.app.presentation.main.home.viewparam

import com.caracrepair.app.models.responsemodel.HomePageResponse

data class RepairShopSliderItem(
    val image: String,
    val description: String
) {
    constructor(model: HomePageResponse.CarShopModel) : this(
        model.imageUrl.orEmpty(),
        model.description.orEmpty()
    )
}