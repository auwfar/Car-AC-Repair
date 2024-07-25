package com.caracrepair.app.presentation.servicedetail.viewparam

import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.presentation.repairshopdetail.viewparam.RepairShopDetail

class ServiceDetail(
    val orderId: Int,
    val orderTime: String,
    val carName: String,
    val carDistance: String,
    val serviceTime: String,
    val repairShop: RepairShopDetail,
    val complaint: String,
    val serviceType: String,
    val pickUpAddress: String,
    val mechanicName: String,
    val status: String,
    val isAbleToPay: Boolean,
    val isAbleToReschedule: Boolean,
    val serviceLogs: List<ServiceLogItem>
) {
    constructor(response: ServiceDetailResponse?) : this(
        orderId = response?.orderId ?: 0,
        orderTime = response?.orderTime.orEmpty(),
        carName = response?.carName.orEmpty(),
        carDistance = response?.carDistance.orEmpty(),
        serviceTime = response?.serviceTime.orEmpty(),
        repairShop = RepairShopDetail(response?.repairShop),
        complaint = response?.complaint.orEmpty(),
        serviceType = response?.serviceType.orEmpty(),
        pickUpAddress = response?.pickUpAddress.orEmpty(),
        mechanicName = response?.mechanicName.orEmpty(),
        status = response?.status.orEmpty(),
        isAbleToPay = response?.isAbleToPay ?: false,
        isAbleToReschedule = response?.isAbleToReschedule ?: false,
        serviceLogs = response?.serviceLogs?.map { ServiceLogItem(it) }.orEmpty()
    )
}