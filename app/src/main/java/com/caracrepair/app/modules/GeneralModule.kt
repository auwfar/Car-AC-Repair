package com.caracrepair.app.modules

import android.content.Context
import com.caracrepair.app.utils.ApiResponseUtil
import com.caracrepair.app.utils.FirebaseUtil
import com.caracrepair.app.utils.preferences.GeneralPreference
import com.caracrepair.app.utils.GsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeneralModule {
    @Provides
    @Singleton
    fun provideGsonUtil(): GsonUtil {
        return GsonUtil()
    }

    @Provides
    @Singleton
    fun provideApiResponseUtil(gsonUtil: GsonUtil): ApiResponseUtil {
        return ApiResponseUtil(gsonUtil)
    }

    @Provides
    @Singleton
    fun provideGeneralPreference(@ApplicationContext appContext: Context, gsonUtil: GsonUtil): GeneralPreference {
        return GeneralPreference(appContext, gsonUtil)
    }

    @Provides
    fun provideFirebaseUtil(): FirebaseUtil {
        return FirebaseUtil()
    }
}