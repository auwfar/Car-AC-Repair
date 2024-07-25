package com.caracrepair.app.presentation.main.history.viewparam

import com.caracrepair.app.models.response.BookingHistoryResponse

data class HistoryItem(
    val id: Int,
    val orderDate: String,
    val carName: String,
    val repairShopImage: String,
    val serviceDate: String,
    val status: String
) {
    constructor(response: BookingHistoryResponse) : this(
        response.id ?: 0,
        response.orderTime.orEmpty(),
        response.carName.orEmpty(),
        response.repairShopImage.orEmpty(),
        response.serviceTime.orEmpty(),
        response.status.orEmpty()
    )
}