package com.stepikbrowser.domain.stepik

import android.util.Log
import androidx.lifecycle.LiveData
import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val stepikRepository: StepikRepository
) {
    suspend fun getCourses(page: Int?, order: String?): List<Course>? = stepikRepository.getCourses(page, order)

    suspend fun getCourseDetails(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    suspend fun bookmarkCourse(course: Course, isBookmarked: Boolean?) {
        stepikRepository.bookmarkCourse(course, isBookmarked)
    }
    fun getBookmarkedCourses(orderBy: String): LiveData<List<Course>> {
        return stepikRepository.getBookmarkedCourses(orderBy)
    }
    fun getBookmarkedCoursesIds(): LiveData<List<Int>> {
        return stepikRepository.getBookmarkedCoursesIds()
    }
}