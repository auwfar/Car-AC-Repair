package com.caracrepair.app.presentation.servicedetail.viewparam

import android.os.Parcelable
import com.caracrepair.app.consts.ServiceStatusConst
import com.caracrepair.app.models.response.FeeDetailResponse
import com.caracrepair.app.models.response.ServiceLogResponse
import com.caracrepair.app.utils.DateUtil
import com.caracrepair.app.utils.NumberUtil
import com.caracrepair.app.utils.SimpleDateUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceLogItem(
    val title: String,
    val description: String,
    val time: String,
    val fee: FeeItem?
) : Parcelable {
    constructor(response: ServiceLogResponse?, fee: List<FeeDetailResponse>?, serviceType: String) : this(
        response?.title.orEmpty(),
        ServiceStatusConst.getDescription(response?.status.orEmpty(), serviceType).takeIf {
            ServiceStatusConst.isShowStaticDescription(response?.status.orEmpty())
        } ?: response?.description.orEmpty(),
        SimpleDateUtil.parseDate(response?.logTime.orEmpty(), DateUtil.FROM_SERVER, DateUtil.HOURS_DAY_FULL_MONTH_YEAR).orEmpty(),
        fee?.let { FeeItem(it) }
    )
}

@Parcelize
data class FeeItem(
    val feeTotal: String,
    val fees: List<FeeDetailItem>
) : Parcelable {
    constructor(response: List<FeeDetailResponse>?) : this(
        NumberUtil.convertToRupiah(response?.sumOf { it.total ?: 0 } ?: 0),
        response?.map { FeeDetailItem(it) }.orEmpty()
    )
}