package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class ChangeProfileBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
)