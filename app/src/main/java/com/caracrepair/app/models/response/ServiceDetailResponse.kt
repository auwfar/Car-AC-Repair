package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceDetailResponse(
    @SerializedName("id")
    val orderId: String?,
    @SerializedName("createdAt")
    val orderTime: String?,
    @SerializedName("carshop_image_url")
    val repairShopImage: String?,
    @SerializedName("car_name")
    val carName: String?,
    @SerializedName("distance")
    val carDistance: String?,
    @SerializedName("service_at")
    val serviceTime: String?,
    @SerializedName("company_branch")
    val repairShop: RepairShopDetailResponse?,
    @SerializedName("description")
    val complaint: String?,
    @SerializedName("service_type")
    val serviceType: String?,
    @SerializedName("customer_address")
    val pickUpAddress: String?,
    @SerializedName("mechanic_name")
    val mechanicName: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("is_able_to_pay")
    val isAbleToPay: Boolean?,
    @SerializedName("is_able_to_reschedule")
    val isAbleToReschedule: Boolean?,
    @SerializedName("order_logs")
    val serviceLogs: List<ServiceLogResponse>?,
    @SerializedName("items")
    val fee: List<FeeDetailResponse>?
)