package com.stepikbrowser.domain.auth

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticatedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
    fun logout() {
        authRepository.logout()
    }
}