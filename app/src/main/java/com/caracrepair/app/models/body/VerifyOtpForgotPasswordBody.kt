package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class VerifyOtpForgotPasswordBody(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("user_id")
    val userId: String
)