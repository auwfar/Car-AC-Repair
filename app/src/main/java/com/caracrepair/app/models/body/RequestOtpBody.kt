package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class RequestOtpBody(
    @SerializedName("emailOrPhone")
    val phoneNumber: String
)