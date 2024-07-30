package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("id")
    val orderId: String?,
    @SerializedName("createdAt")
    val orderTime: String?,
    @SerializedName("carshop_image_url")
    val repairShopImage: String?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("service_at")
    val serviceTime: String?,
    @SerializedName("status")
    val status: String?
)