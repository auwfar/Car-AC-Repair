package com.caracrepair.app.models.response

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(
    @SerializedName("link")
    val imageUrl: String?
)