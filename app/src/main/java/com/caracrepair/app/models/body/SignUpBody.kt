package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class SignUpBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String
)