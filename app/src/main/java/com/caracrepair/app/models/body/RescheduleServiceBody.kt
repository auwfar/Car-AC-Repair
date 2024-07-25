package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class RescheduleServiceBody(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("carshop_id")
    val repairShopId: Int,
    @SerializedName("service_date")
    val serviceDate: String,
    @SerializedName("service_time")
    val serviceTime: String
)