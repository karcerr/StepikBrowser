package com.stepikbrowser.domain.stepik

import androidx.lifecycle.LiveData

interface StepikRepository {
    suspend fun authUser(): String
    fun saveAccessToken(token: String)
    suspend fun getCourses(page: Int?, order: String?): List<Course>?
    suspend fun getCourseDetails(courseId: Int): Course?
    suspend fun bookmarkCourse(course: Course, isBookmarked: Boolean?)
    fun getBookmarkedCourses(orderBy: String): LiveData<List<Course>>
    fun getBookmarkedCoursesIds(): LiveData<List<Int>>
}