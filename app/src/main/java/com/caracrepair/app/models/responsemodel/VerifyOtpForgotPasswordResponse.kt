package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class VerifyOtpForgotPasswordResponse(
    @SerializedName("user_id")
    val userId: Int?
)