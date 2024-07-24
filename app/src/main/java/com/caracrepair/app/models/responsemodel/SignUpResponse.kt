package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("user_id")
    val userId: Int?
)