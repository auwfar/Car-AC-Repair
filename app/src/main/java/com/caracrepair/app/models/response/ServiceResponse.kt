package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("id")
    val orderId: String?,
    @SerializedName("createdAt")
    val orderTime: String?,
    @SerializedName("company_branch")
    val repairShop: RepairShopResponse?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("service_at")
    val serviceAt: String?,
    @SerializedName("status_label")
    val status: String?
)