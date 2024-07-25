package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ForgotPasswordBody(
    @SerializedName("phone_number")
    val phoneNumber: String
)