package com.stepikbrowser.data.courses.di

import com.stepikbrowser.data.courses.StepikApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StepikApiModule {
    @Provides
    @Singleton
    fun provideStepikApiService(retrofit: Retrofit): StepikApiService =
        retrofit.create(StepikApiService::class.java)
}