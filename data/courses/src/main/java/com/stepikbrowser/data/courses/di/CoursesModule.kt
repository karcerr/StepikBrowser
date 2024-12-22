package com.stepikbrowser.data.courses.di

import com.stepikbrowser.data.courses.CourseRepositoryImpl
import com.stepikbrowser.domain.courses.CourseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoursesModule {
    @Binds
    abstract fun bindCoursesRepository(
        courseRepositoryImpl: CourseRepositoryImpl
    ): CourseRepository
}
