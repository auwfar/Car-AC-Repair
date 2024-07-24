package com.caracrepair.app.modules

import android.content.Context
import com.caracrepair.app.BuildConfig
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.network.interceptor.LoginInterceptor
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideOkHttpClient(preference: GeneralPreference, @ApplicationContext appContext: Context): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(LoginInterceptor(preference))
        if (BuildConfig.DEBUG) addInterceptor(ChuckerInterceptor(appContext))
        readTimeout(30, TimeUnit.SECONDS)
    }.build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}