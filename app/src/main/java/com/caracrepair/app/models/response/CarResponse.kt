package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class CarResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("plat_number")
    val licenseNumber: String?,
    @SerializedName("car_date")
    val carYear: String?
)