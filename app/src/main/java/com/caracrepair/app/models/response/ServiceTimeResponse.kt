package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceTimeResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("is_available")
    val isAvailable: Boolean?
)