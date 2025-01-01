package com.stepikbrowser.data.stepik

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.stepikbrowser.data.stepik.util.TokenManager
import com.stepikbrowser.domain.stepik.StepikRepository
import com.stepikbrowser.domain.stepik.Course
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class StepikRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: StepikApiService,
    private val authService: StepikAuthService,
    private val tokenManager: TokenManager
): StepikRepository {
    override suspend fun authUser(): String {
        val response = authService.getAccessToken(
            clientId = "uFkdnH5QoHJt6vvnEEEe09bi7fLkHcfCCtK8PoXd",
            clientSecret = "AseqxRQdIfEP6GLZH598mbmrGKLPT3xZZZKEM0ZDAaThWMPRorJjoF2isLtWEa9jnWdD5uVEw1tge1fq9K0OYnLsLIv9g1AA6o16GPGHlHzEx9e6YCu9KHnr7GtWGGXR"
        )
        Log.d("Stepik Auth Logger", response.toString())
        return response.accessToken
    }

    override fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    override suspend fun getCourses(page: Int?, order: String?): List<Course>? {
        val response = apiService.getCourses(
            page = page,
            order = order?: "create_date"
        )
        Log.d("Courses Logger", response.courses.toString())
        return response.courses
    }

    override suspend fun getCourseDetails(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteCourse(courseId: Int): Boolean? {
        TODO("Not yet implemented")
    }
}