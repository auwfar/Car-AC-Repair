package com.caracrepair.app.network.model

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("data")
    val data: T?,
    @SerializedName("meta")
    val meta: MetaModel?
)

data class MetaModel(
    @SerializedName("current")
    val current: Int?,
    @SerializedName("pages")
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
    val status: Boolean?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("report")
    val report: String?
)