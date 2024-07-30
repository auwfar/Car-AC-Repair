package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class RescheduleServiceBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("carshop_id")
    val repairShopId: String,
    @SerializedName("service_at")
    val serviceAt: String
)