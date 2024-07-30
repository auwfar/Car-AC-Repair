package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceTimeResponse(
    @SerializedName("time")
    val time: String?,
    @SerializedName("available")
    val isAvailable: Boolean?
)