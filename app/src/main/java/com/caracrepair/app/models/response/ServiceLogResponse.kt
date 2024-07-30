package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceLogResponse(
    @SerializedName("createdAt")
    val logTime: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("status")
    val status: String?
)