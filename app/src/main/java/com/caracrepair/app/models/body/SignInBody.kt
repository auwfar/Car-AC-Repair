package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class SignInBody(
    @SerializedName("emailOrPhone")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcm_token")
    val fcmToken: String
)