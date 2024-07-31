package com.caracrepair.app.presentation.main.home.viewparam

import com.caracrepair.app.models.response.HomePageResponse
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.SimpleDateUtil

data class LastServiceItem(
    val id: String,
    val carName: String,
    val serviceDate: String,
    val distance: String
) {
    constructor(model: HomePageResponse.LastServiceModel) : this(
        model.orderId.orEmpty(),
        model.carName.orEmpty(),
        SimpleDateUtil.parseDate(model.orderTime.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        model.carDistance.orEmpty()
    )
}