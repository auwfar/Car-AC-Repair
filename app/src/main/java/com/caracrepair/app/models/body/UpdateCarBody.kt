package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class UpdateCarBody(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("license_number")
    val licenseNumber: String,
    @SerializedName("year")
    val year: String
)