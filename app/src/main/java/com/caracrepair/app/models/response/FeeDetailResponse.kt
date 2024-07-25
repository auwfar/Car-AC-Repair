package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class FeeDetailResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("total")
    val total: String?
)