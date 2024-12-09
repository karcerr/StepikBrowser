package com.stepikbrowser.domain.auth

import javax.inject.Inject

class CheckUserLoginStatusUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepo.isUserLoggedIn()
    }
}