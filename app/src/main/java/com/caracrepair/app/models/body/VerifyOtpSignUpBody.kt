package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class VerifyOtpSignUpBody(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("user_id")
    val userId: String
)