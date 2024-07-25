package com.caracrepair.app.presentation.servicepayment.viewparam

import com.caracrepair.app.models.response.ServicePaymentResponse
import com.caracrepair.app.presentation.repairshopdetail.viewparam.RepairShopDetail
import com.caracrepair.app.presentation.servicedetail.viewparam.FeeItem

data class ServicePayment(
    val orderId: Int,
    val orderTime: String,
    val carName: String,
    val carDistance: String,
    val serviceTime: String,
    val repairShop: RepairShopDetail,
    val mechanicName: String,
    val fee: FeeItem
) {
    constructor(response: ServicePaymentResponse?) : this(
        response?.orderId ?: 0,
        response?.orderTime.orEmpty(),
        response?.carName.orEmpty(),
        response?.carDistance.orEmpty(),
        response?.serviceTime.orEmpty(),
        RepairShopDetail(response?.repairShop),
        response?.mechanicName.orEmpty(),
        FeeItem(response?.fee)
    )
}