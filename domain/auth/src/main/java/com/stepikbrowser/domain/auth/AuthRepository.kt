package com.stepikbrowser.domain.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun getCurrentUser(): FirebaseUser?
    suspend fun login(email: String, password: String): AuthResult?
    suspend fun register(email: String, password: String): AuthResult?
    fun logout()
}