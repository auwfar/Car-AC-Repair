package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class BookingServiceBody(
    @SerializedName("customer_id")
    val userId: String,
    @SerializedName("customer_name")
    val userName: String,
    @SerializedName("customer_car_id")
    val carId: String,
    @SerializedName("car_plat_number")
    val carLicenseNumber: String,
    @SerializedName("car_name")
    val carName: String,
    @SerializedName("car_date")
    val carYear: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("customer_address")
    val address: String,
    @SerializedName("distance")
    val carDistance: Int,
    @SerializedName("description")
    val complaint: String,
    @SerializedName("company_branch_id")
    val repairShopId: String,
    @SerializedName("service_type")
    val serviceType: String,
    @SerializedName("service_at")
    val serviceTime: String
)