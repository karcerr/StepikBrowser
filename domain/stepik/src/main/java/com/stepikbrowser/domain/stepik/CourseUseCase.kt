package com.stepikbrowser.domain.stepik

import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val stepikRepository: StepikRepository
) {
    suspend fun getCourses(page: Int?, order: String?): List<Course>? = stepikRepository.getCourses(page, order)

    suspend fun getCourseDetails(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    suspend fun bookmarkCourse(course: Course) {
        stepikRepository.bookmarkCourse(course, course.bookmarked?: false)
    }
}