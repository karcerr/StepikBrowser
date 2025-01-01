package com.stepikbrowser.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.stepikbrowser.domain.auth.AuthRepository
import com.stepikbrowser.domain.auth.AuthUseCase
import com.stepikbrowser.domain.auth.AuthenticatedUseCase
import com.stepikbrowser.domain.auth.CheckUserLoginStatusUseCase
import com.stepikbrowser.domain.stepik.StepikRepository
import com.stepikbrowser.domain.stepik.CourseUseCase
import com.stepikbrowser.domain.stepik.StepikAuthUseCase

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
        stepikRepository: StepikRepository
    ): CourseUseCase {
        return CourseUseCase(stepikRepository)
    }
    @Provides
    fun provideStepikAuthUseCase(
        stepikRepository: StepikRepository
    ): StepikAuthUseCase {
        return StepikAuthUseCase(stepikRepository)
    }
}