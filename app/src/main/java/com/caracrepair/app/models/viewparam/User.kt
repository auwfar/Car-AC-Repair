package com.caracrepair.app.models.viewparam

import com.caracrepair.app.models.responsemodel.SignInResponse
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("token")
    val token: String
) {
    constructor(signInResponse: SignInResponse?) : this(
        userId = signInResponse?.userId ?: 0,
        phoneNumber = signInResponse?.phoneNumber.orEmpty(),
        name = signInResponse?.name.orEmpty(),
        profileImage = signInResponse?.profileImage.orEmpty(),
        token = signInResponse?.token.orEmpty(),
    )
}