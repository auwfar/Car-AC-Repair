package com.caracrepair.app.utils

import retrofit2.HttpException
import javax.inject.Inject

class ApiResponseUtil @Inject constructor(val gsonUtil: GsonUtil) {
    inline fun <reified T> getErrorResponse(error: HttpException): T? {
        return try {
            gsonUtil.fromJson<T>(error.response()?.errorBody()?.string())
        } catch (e: Exception) {
            null
        }
    }
}