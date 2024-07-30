package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class BookingHistoryResponse(
    @SerializedName("orders")
    val booking: BookingResponse?
)