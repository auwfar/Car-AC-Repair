package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("user_id")
    val userId: String?
)