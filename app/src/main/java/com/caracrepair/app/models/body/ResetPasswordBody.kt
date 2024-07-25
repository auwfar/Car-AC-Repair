package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ResetPasswordBody(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("new_password")
    val newPassword: String
)