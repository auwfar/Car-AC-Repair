package com.caracrepair.app.modules

import android.content.Context
import com.caracrepair.app.BuildConfig
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.network.RetrofitProvider
import com.caracrepair.app.network.interceptor.LoginInterceptor
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Inject
    lateinit var appContext: Context
    @Inject
    lateinit var preference: GeneralPreference

    private val retrofitProvider by lazy {
        RetrofitProvider(getOkHttpClient())
    }

    @Provides
    fun provideApiService(): ApiService {
        return retrofitProvider.getApiService()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(LoginInterceptor(preference))
            if (BuildConfig.DEBUG) addInterceptor(ChuckerInterceptor(appContext))
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }
}