package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class UpdateCarBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("plat_number")
    val licenseNumber: String,
    @SerializedName("car_date")
    val year: String
)