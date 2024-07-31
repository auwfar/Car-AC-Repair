package com.caracrepair.app.presentation.main.history.viewparam

import com.caracrepair.app.models.response.ServiceResponse
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.SimpleDateUtil

data class HistoryItem(
    val id: String,
    val orderAt: String,
    val carName: String,
    val repairShopImage: String,
    val serviceAt: String,
    val status: String
) {
    constructor(response: ServiceResponse?) : this(
        response?.orderId.orEmpty(),
        SimpleDateUtil.parseDate(response?.orderTime.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        response?.carName.orEmpty(),
        response?.repairShop?.imageUrl.orEmpty(),
        SimpleDateUtil.parseDate(response?.serviceAt.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        response?.status.orEmpty()
    )
}