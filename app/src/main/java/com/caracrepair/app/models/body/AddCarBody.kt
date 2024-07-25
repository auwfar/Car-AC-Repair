package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class AddCarBody(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("license_number")
    val licenseNumber: String,
    @SerializedName("year")
    val year: String
)