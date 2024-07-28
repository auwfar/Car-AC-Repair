package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class BookingServiceBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("service_type")
    val serviceType: String,
    @SerializedName("car_id")
    val carId: Int,
    @SerializedName("address_id")
    val addressId: Int,
    @SerializedName("car_distance")
    val carDistance: Int,
    @SerializedName("complaint")
    val complaint: String,
    @SerializedName("carshop_id")
    val repairShopId: String,
    @SerializedName("service_date")
    val serviceDate: String,
    @SerializedName("service_time")
    val serviceTime: String
)