package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServicePaymentResponse(
    @SerializedName("order_id")
    val orderId: Int?,
    @SerializedName("order_time")
    val orderTime: String?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("car_distance")
    val carDistance: String?,
    @SerializedName("service_time")
    val serviceTime: String?,
    @SerializedName("carshop")
    val repairShop: RepairShopDetailResponse?,
    @SerializedName("mechanic_name")
    val mechanicName: String?,
    @SerializedName("fee")
    val fee: FeeResponse?
)