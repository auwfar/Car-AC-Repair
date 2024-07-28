package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("user_id")
    val userId: String?
)