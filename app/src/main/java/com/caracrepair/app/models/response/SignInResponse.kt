package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("id")
    val userId: String?,
    @SerializedName("phone")
    val phoneNumber: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val profileImage: String?,
    @SerializedName("token")
    val token: String?
)