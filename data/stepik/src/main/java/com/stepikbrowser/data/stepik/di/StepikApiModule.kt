package com.stepikbrowser.data.stepik.di

import android.content.Context
import com.stepikbrowser.data.stepik.StepikApiService
import com.stepikbrowser.data.stepik.StepikAuthService
import com.stepikbrowser.data.stepik.util.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StepikApiModule {
    @Provides
    @Singleton
    fun provideStepikApiService(@ApiRetrofit retrofit: Retrofit): StepikApiService =
        retrofit.create(StepikApiService::class.java)

    @Provides
    @Singleton
    fun provideStepikAuthService(@AuthRetrofit retrofit: Retrofit): StepikAuthService =
        retrofit.create(StepikAuthService::class.java)

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiRetrofit