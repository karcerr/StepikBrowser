package com.stepikbrowser.data.courses

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.stepikbrowser.domain.courses.Course
import com.stepikbrowser.domain.courses.CourseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: StepikApiService
): CourseRepository {
    override suspend fun getCourses(page: Int?, order: String?): List<Course>? {
        val response = apiService.getCourses(
            page = page?: 1,
            order = order?: ""
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