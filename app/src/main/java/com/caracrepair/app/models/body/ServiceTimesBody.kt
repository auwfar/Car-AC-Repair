package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ServiceTimesBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("carshop_id")
    val repairShopId: String,
    @SerializedName("service_date")
    val fcmToken: String
)