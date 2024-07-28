package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class BookingServiceBody(
    @SerializedName("customer_id")
    val userId: String,
    @SerializedName("service_type")
    val serviceType: String,
    @SerializedName("customer_car_id")
    val carId: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("distance")
    val carDistance: Int,
    @SerializedName("description")
    val complaint: String,
    @SerializedName("company_branch_id")
    val repairShopId: String,
    @SerializedName("service_date")
    val serviceDate: String,
    @SerializedName("service_time")
    val serviceTime: String
)