package com.stepikbrowser.data.user

import com.google.firebase.auth.FirebaseAuth
import com.stepikbrowser.domain.auth.AuthRepository
import com.stepikbrowser.domain.auth.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun login(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun register(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}