package com.stepikbrowser.data.stepik.di

import com.stepikbrowser.data.stepik.StepikRepositoryImpl
import com.stepikbrowser.domain.stepik.StepikRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoursesModule {
    @Binds
    abstract fun bindCoursesRepository(
        stepikRepositoryImpl: StepikRepositoryImpl
    ): StepikRepository
}
