package com.stepikbrowser.domain.auth

data class User(
    val id: String,
    val email: String,
    val name: String? = null,
    val profilePictureUrl: String? = null
)
