package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class ServiceLogResponse(
    @SerializedName("log_time")
    val logTime: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("fee")
    val fee: FeeResponse?
)