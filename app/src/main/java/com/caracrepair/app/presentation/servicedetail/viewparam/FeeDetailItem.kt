package com.caracrepair.app.presentation.servicedetail.viewparam

import com.caracrepair.app.models.response.FeeDetailResponse

data class FeeDetailItem(
    val feeName: String,
    val feeTotal: String
) {
    constructor(response: FeeDetailResponse?) : this(
        response?.name.orEmpty(),
        response?.total.orEmpty()
    )
}