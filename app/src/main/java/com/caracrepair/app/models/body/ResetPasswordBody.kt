package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ResetPasswordBody(
    @SerializedName("emailOrPhone")
    val phoneNumber: String,
    @SerializedName("newPassword")
    val newPassword: String
)