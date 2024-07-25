package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class BookingHistoryResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("order_time")
    val orderTime: String?,
    @SerializedName("carshop_image_url")
    val repairShopImage: String?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("service_time")
    val serviceTime: String?,
    @SerializedName("status")
    val status: String?
)