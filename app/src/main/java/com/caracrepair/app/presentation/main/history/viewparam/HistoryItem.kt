package com.caracrepair.app.presentation.main.history.viewparam

import com.caracrepair.app.models.response.BookingResponse
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.SimpleDateUtil

data class HistoryItem(
    val id: String,
    private val orderDate: String,
    val carName: String,
    val repairShopImage: String,
    private val serviceDate: String,
    val status: String
) {
    constructor(response: BookingResponse?) : this(
        response?.id.orEmpty(),
        response?.orderTime.orEmpty(),
        response?.carName.orEmpty(),
        response?.repairShopImage.orEmpty(),
        response?.serviceTime.orEmpty(),
        response?.status.orEmpty()
    )

    fun getOrderDate(): String {
        return SimpleDateUtil.parseDate(orderDate, DateUtil.SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty()
    }

    fun getServiceDate(): String {
        return SimpleDateUtil.parseDate(serviceDate, DateUtil.SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty()
    }
}