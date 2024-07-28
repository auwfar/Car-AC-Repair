package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class AddCarBody(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("plat_number")
    val licenseNumber: String,
    @SerializedName("car_date")
    val year: String
)