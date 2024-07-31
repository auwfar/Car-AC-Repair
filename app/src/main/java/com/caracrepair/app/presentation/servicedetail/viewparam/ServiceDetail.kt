package com.caracrepair.app.presentation.servicedetail.viewparam

import android.os.Parcelable
import com.caracrepair.app.consts.ServiceStatusConst
import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.presentation.repairshopdetail.viewparam.RepairShopDetail
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.SimpleDateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
class ServiceDetail(
    val orderId: String,
    val orderTime: String,
    val carName: String,
    val carDistance: String,
    val serviceAt: String,
    val repairShop: RepairShopDetail,
    val complaint: String,
    val serviceType: String,
    val pickUpAddress: String,
    val mechanicName: String,
    val status: String,
    val isAbleToPay: Boolean,
    val isAbleToReschedule: Boolean,
    val serviceLogs: List<ServiceLogItem>,
    val fee: FeeItem? = null
) : Parcelable {
    constructor(response: ServiceDetailResponse?) : this(
        orderId = response?.orderId.orEmpty(),
        orderTime = SimpleDateUtil.parseDate(response?.orderTime.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        carName = response?.carName.orEmpty(),
        carDistance = response?.carDistance.orEmpty() +" km",
        serviceAt = SimpleDateUtil.parseDate(response?.serviceAt.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        repairShop = RepairShopDetail(response?.repairShop),
        complaint = response?.complaint.orEmpty(),
        serviceType = response?.serviceType.orEmpty(),
        pickUpAddress = response?.pickUpAddress.orEmpty(),
        mechanicName = response?.mechanicName.orEmpty(),
        status = response?.status.orEmpty(),
        isAbleToPay = response?.isAbleToPay ?: false,
        isAbleToReschedule = response?.isAbleToReschedule ?: false,
        serviceLogs = response?.serviceLogs?.map { log ->
            ServiceLogItem(
                log,
                response.fee?.takeIf { ServiceStatusConst.isShowFee(log.status.orEmpty()) },
                response.serviceType.orEmpty()
            )
        }.orEmpty(),
        fee = response?.fee?.let { FeeItem(it) }
    )
}