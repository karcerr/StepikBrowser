package com.stepikbrowser.domain.auth

import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(username: String, password: String): AuthResult? {
        return authRepository.login(username, password)
    }
    suspend fun register(username: String, password: String): AuthResult? {
        return authRepository.register(username, password)
    }
}