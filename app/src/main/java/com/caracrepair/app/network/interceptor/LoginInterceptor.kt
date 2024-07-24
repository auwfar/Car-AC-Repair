package com.caracrepair.app.network.interceptor

import com.caracrepair.app.utils.preferences.GeneralPreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LoginInterceptor(
    private val preference: GeneralPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = handleLogin(chain.request())
        return chain.proceed(request)
    }

    private fun handleLogin(request: Request): Request {
        return request.newBuilder().apply {
            val accessToken = preference.getUser()?.token
            if (accessToken != null) {
                addHeader("Authorization", "Bearer $accessToken")
            }
        }.build()
    }
}