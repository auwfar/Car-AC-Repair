package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class VerifyOtpBody(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("emailOrPhone")
    val phoneNumber: String
)