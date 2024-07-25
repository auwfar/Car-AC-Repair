package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class FeeResponse(
    @SerializedName("total")
    val total: String?,
    @SerializedName("detail")
    val detail: List<FeeDetailResponse>?
)