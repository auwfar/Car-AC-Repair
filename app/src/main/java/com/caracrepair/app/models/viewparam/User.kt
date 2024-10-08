package com.caracrepair.app.models.viewparam

import com.caracrepair.app.models.response.SignInResponse
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("token")
    val token: String
) {
    constructor(response: SignInResponse?) : this(
        userId = response?.userId.orEmpty(),
        phoneNumber = response?.phoneNumber.orEmpty(),
        name = response?.name.orEmpty(),
        profileImage = response?.profileImage.orEmpty(),
        token = response?.token.orEmpty(),
    )
}