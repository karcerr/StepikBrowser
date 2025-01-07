package com.stepikbrowser.domain.stepik

interface StepikRepository {
    suspend fun authUser(): String
    fun saveAccessToken(token: String)
    suspend fun getCourses(page: Int?, order: String?): List<Course>?
    suspend fun getCourseDetails(courseId: Int): Course?
    suspend fun bookmarkCourse(course: Course, isBookmarked: Boolean): Boolean?
}