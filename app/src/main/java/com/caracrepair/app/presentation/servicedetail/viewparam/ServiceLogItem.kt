package com.caracrepair.app.presentation.servicedetail.viewparam

import com.caracrepair.app.models.response.FeeResponse
import com.caracrepair.app.models.response.ServiceLogResponse

data class ServiceLogItem(
    val title: String,
    val description: String,
    val time: String,
    val fee: FeeItem? = null
) {
    constructor(response: ServiceLogResponse?) : this(
        response?.title.orEmpty(),
        response?.description.orEmpty(),
        response?.logTime.orEmpty(),
        response?.fee?.let { FeeItem(it) }
    )
}

data class FeeItem(
    val feeTotal: String,
    val fees: List<FeeDetailItem>
) {
    constructor(response: FeeResponse?) : this(
        response?.total.orEmpty(),
        response?.detail?.map { FeeDetailItem(it) }.orEmpty()
    )
}