package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class RepairShopDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("admin_phone_number")
    val adminPhoneNumber: String?,
    @SerializedName("location")
    val location: LocationResponse?
)