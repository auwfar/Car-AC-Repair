package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class CarResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("license_number")
    val licenseNumber: String?,
    @SerializedName("car_year")
    val carYear: String?
)