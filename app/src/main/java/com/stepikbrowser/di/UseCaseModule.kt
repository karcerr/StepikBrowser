package com.stepikbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.stepikbrowser.domain.auth.AuthRepository
import com.stepikbrowser.domain.auth.AuthUseCase
import com.stepikbrowser.domain.auth.AuthenticatedUseCase
import com.stepikbrowser.domain.auth.CheckUserLoginStatusUseCase
import com.stepikbrowser.domain.courses.CourseRepository
import com.stepikbrowser.domain.courses.CourseUseCase

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideCheckUserLoginStatusUseCase(
        authRepository: AuthRepository
    ): CheckUserLoginStatusUseCase {
        return CheckUserLoginStatusUseCase(authRepository)
    }
    @Provides
    fun provideAuthUseCase(
        authRepository: AuthRepository
    ): AuthUseCase {
        return AuthUseCase(authRepository)
    }
    @Provides
    fun provideAuthenticatedUseCase(
        authRepository: AuthRepository
    ): AuthenticatedUseCase {
        return AuthenticatedUseCase(authRepository)
    }
    @Provides
    fun provideCourseUseCase(
        courseRepository: CourseRepository
    ): CourseUseCase {
        return CourseUseCase(courseRepository)
    }

}