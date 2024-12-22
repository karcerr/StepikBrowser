package com.stepikbrowser.domain.courses

interface CourseRepository {
    suspend fun getCourses(page: Int?, order: String?): List<Course>?
    suspend fun getCourseDetails(courseId: Int): Course?
    suspend fun favoriteCourse(courseId: Int): Boolean?
}