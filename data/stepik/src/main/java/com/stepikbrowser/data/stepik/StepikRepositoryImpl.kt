package com.stepikbrowser.data.stepik

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.stepikbrowser.data.stepik.util.TokenManager
import com.stepikbrowser.domain.stepik.StepikRepository
import com.stepikbrowser.domain.stepik.Course
import javax.inject.Inject
import kotlinx.coroutines.tasks.await
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class StepikRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: StepikApiService,
    private val authService: StepikAuthService,
    private val tokenManager: TokenManager,
    context: Context
): StepikRepository {
    private val properties = Properties()
    init {
        try {
            val inputStream = context.assets.open("secrets.properties")
            properties.load(InputStreamReader(inputStream))
        } catch (e: IOException) {
            Log.e("Stepik Auth", "Failed to load configuration", e)
        }
    }
    override suspend fun authUser(): String {
        val response = authService.getAccessToken(
            clientId =  properties.getProperty("CLIENT_ID"),
            clientSecret = properties.getProperty("CLIENT_SECRET")
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
        return response.courses
    }

    override suspend fun getCourseDetails(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    override suspend fun bookmarkCourse(courseId: Int): Boolean? {
        TODO("Not yet implemented")
    }
}