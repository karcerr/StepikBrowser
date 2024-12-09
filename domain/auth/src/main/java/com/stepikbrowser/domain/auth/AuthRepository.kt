package com.stepikbrowser.domain.auth

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String): Result<User>
    fun logout()
}