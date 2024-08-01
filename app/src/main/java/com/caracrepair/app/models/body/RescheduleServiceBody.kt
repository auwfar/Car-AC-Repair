package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class RescheduleServiceBody(
    @SerializedName("company_branch_id")
    val repairShopId: String,
    @SerializedName("service_at")
    val serviceAt: String
)