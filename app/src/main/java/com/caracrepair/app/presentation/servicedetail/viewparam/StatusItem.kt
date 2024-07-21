package com.caracrepair.app.presentation.servicedetail.viewparam

data class StatusItem(
    val statusTitle: String,
    val statusDescription: String,
    val statusTime: String,
    val fee: FeeItem? = null
)

data class FeeItem(
    val feeTotal: String,
    val fees: List<FeeDetailItem>
)