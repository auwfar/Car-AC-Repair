package com.caracrepair.app.models.body

import com.google.gson.annotations.SerializedName

data class DeleteAddressBody(
    @SerializedName("id")
    val id: Int
)