package com.stepikbrowser.domain.stepik

import javax.inject.Inject

class StepikAuthUseCase @Inject constructor(
    private val stepikRepository: StepikRepository
) {
    suspend fun authApi(): String = stepikRepository.authUser()

    fun saveAccessToken(token: String) {
        stepikRepository.saveAccessToken(token)
    }
}