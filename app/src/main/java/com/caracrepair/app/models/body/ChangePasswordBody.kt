package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String
)