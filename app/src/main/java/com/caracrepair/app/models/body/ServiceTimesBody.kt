package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ServiceTimesBody(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("carshop_id")
    val carshop_id: Int,
    @SerializedName("service_date")
    val fcmToken: String
)