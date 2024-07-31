package com.caracrepair.app.presentation.servicedetail.viewparam

import android.os.Parcelable
import com.caracrepair.app.models.response.FeeDetailResponse
import com.caracrepair.app.utils.NumberUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeeDetailItem(
    val feeName: String,
    val feeTotal: String
) : Parcelable {
    constructor(response: FeeDetailResponse?) : this(
        response?.name.orEmpty(),
        NumberUtil.convertToRupiah(response?.total ?: 0)
    )
}