package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_image")
    val profileImage: String?,
    @SerializedName("token")
    val token: String?
)