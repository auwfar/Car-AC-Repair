package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ResendOtpForgotPasswordBody(
    @SerializedName("user_id")
    val userId: Int
)