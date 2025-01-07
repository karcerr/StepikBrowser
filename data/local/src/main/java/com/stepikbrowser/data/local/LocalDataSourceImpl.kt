package com.stepikbrowser.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.stepikbrowser.domain.stepik.Course
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(
    private val courseDao: CourseDao
)  {
    suspend fun upsertCourse(course: Course) {
        courseDao.upsertCourse(course.toEntity())
    }

    suspend fun deleteCourse(course: Course) {
        courseDao.deleteCourse(course.toEntity())
    }

    fun getBookmarkedCoursesOrderedByTitle(): LiveData<List<Course>> {
        return courseDao.getBookmarkedCoursesOrderedByTitle().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    fun getBookmarkedCoursesOrderedByRating(): LiveData<List<Course>> {
        return courseDao.getBookmarkedCoursesOrderedByRating().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    fun getBookmarkedCoursesOrderedByCreateDate(): LiveData<List<Course>> {
        return courseDao.getBookmarkedCoursesOrderedByCreateDate().map { entities ->
            entities.map { it.toDomain() }
        }
    }

}

fun <X, Y> LiveData<X>.map(transform: (X) -> Y): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { value ->
        result.value = transform(value)
    }
    return result
}
