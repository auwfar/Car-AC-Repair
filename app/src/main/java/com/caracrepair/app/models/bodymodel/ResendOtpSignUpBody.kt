package com.caracrepair.app.models.bodymodel

import com.google.gson.annotations.SerializedName

data class ResendOtpSignUpBody(
    @SerializedName("user_id")
    val userId: Int
)