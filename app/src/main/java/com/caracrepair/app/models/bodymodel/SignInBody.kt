package com.caracrepair.app.models.bodymodel

import com.google.gson.annotations.SerializedName

data class SignInBody(
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcm_token")
    val fcmToken: String
)