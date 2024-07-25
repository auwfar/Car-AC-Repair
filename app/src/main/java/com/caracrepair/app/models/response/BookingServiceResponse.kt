package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class BookingServiceResponse(
    @SerializedName("order_id")
    val orderId: Int?
)
