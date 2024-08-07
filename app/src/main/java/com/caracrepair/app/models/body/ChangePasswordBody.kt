package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    @SerializedName("password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("confirm_password")
    val newPasswordConfirmation: String
)