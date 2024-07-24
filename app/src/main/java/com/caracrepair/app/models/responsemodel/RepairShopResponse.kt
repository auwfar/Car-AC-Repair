package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class RepairShopResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("admin_phone_number")
    val adminPhoneNumber: String?
)