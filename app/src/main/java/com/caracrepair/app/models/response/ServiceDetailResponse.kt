package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceDetailResponse(
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
    @SerializedName("complaint")
    val complaint: String?,
    @SerializedName("service_type")
    val serviceType: String?,
    @SerializedName("pick_up_address")
    val pickUpAddress: String?,
    @SerializedName("mechanic_name")
    val mechanicName: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("is_able_to_pay")
    val isAbleToPay: Boolean?,
    @SerializedName("is_able_to_reschedule")
    val isAbleToReschedule: Boolean?,
    @SerializedName("service_logs")
    val serviceLogs: List<ServiceLogResponse>?
)