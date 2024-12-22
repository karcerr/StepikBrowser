package com.stepikbrowser.domain.courses

import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val courseRepository: CourseRepository
) {
    suspend fun getCourses(page: Int?, order: String?): List<Course>? {
        return courseRepository.getCourses(page, order)
    }

    suspend fun getCourseDetails(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    suspend fun favoriteCourse(courseId: Int): Boolean? {
        TODO("Not yet implemented")
    }
}