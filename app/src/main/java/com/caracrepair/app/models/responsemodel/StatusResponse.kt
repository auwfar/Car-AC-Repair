package com.caracrepair.app.models.responsemodel

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta")
    val meta: MetaModel?
)

data class MetaModel(
    @SerializedName("total_pages")
    val pages: Int?
)

data class StatusResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?
)

data class ErrorResponse(
    @SerializedName("status")
    val status: Boolean? = false,
    @SerializedName("message")
    val message: String?
)