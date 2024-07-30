package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class RepairShopDetailResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("phone")
    val adminPhoneNumber: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?
)