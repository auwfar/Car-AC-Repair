package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class RescheduleServiceResponse(
    @SerializedName("order_id")
    val orderId: String?
)
