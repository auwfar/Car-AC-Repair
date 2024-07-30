package com.caracrepair.app.presentation.servicedetail.viewparam

import com.caracrepair.app.consts.ServiceStatusConst
import com.caracrepair.app.models.response.FeeDetailResponse
import com.caracrepair.app.models.response.FeeResponse
import com.caracrepair.app.models.response.ServiceLogResponse
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.SimpleDateUtil

data class ServiceLogItem(
    val title: String,
    val description: String,
    val time: String,
    val fee: FeeItem?
) {
    constructor(response: ServiceLogResponse?, fee: List<FeeDetailResponse>?, serviceType: String) : this(
        response?.title.orEmpty(),
        ServiceStatusConst.getDescription(response?.status.orEmpty(), serviceType).takeIf {
            response?.status != ServiceStatusConst.STATUS_CHECKING_CONFIRMATION
        } ?: response?.description.orEmpty(),
        SimpleDateUtil.parseDate(response?.logTime.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        fee?.let { FeeItem(it) }
    )
}

data class FeeItem(
    val feeTotal: String,
    val fees: List<FeeDetailItem>
) {
    constructor(response: List<FeeDetailResponse>?) : this(
        response?.sumOf { it.total ?: 0 }.toString(),
        response?.map { FeeDetailItem(it) }.orEmpty()
    )

    constructor(response: FeeResponse?) : this(
        response?.total.orEmpty(),
        response?.detail?.map { FeeDetailItem(it) }.orEmpty()
    )
}