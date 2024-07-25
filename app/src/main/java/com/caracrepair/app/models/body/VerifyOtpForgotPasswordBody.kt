package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class VerifyOtpForgotPasswordBody(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)