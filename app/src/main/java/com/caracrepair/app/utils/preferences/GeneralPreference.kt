package com.caracrepair.app.utils.preferences

import android.content.Context
import com.caracrepair.app.utils.GsonUtil

private const val KEY_USER_RESPONSE = "KEY_USER_RESPONSE"
private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"

class GeneralPreference(
    context: Context,
    private val gsonUtil: GsonUtil
): BasePreference(context) {

//    fun setUserResponse(value: UserResponse) {
//        writeString(KEY_USER_RESPONSE, gsonUtil.toJson(value))
//    }
//
//    fun getUserResponse(): UserResponse? {
//        val value = readString(KEY_USER_RESPONSE)
//        return gsonUtil.fromJson(value)
//    }

    fun setAccessToken(value: String) {
        writeString(KEY_ACCESS_TOKEN, value)
    }

    fun getAccessToken(): String? {
        return readString(KEY_ACCESS_TOKEN)
    }
}