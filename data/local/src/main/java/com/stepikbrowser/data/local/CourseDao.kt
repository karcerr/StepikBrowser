package com.stepikbrowser.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {
    @Upsert
    suspend fun upsertCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("SELECT * FROM courses ORDER BY createDate DESC")
    fun getBookmarkedCoursesOrderedByCreateDate(): LiveData<List<CourseEntity>>

    @Query("SELECT * FROM courses ORDER BY title ASC")
    fun getBookmarkedCoursesOrderedByTitle(): LiveData<List<CourseEntity>>

    @Query("SELECT * FROM courses ORDER BY rating DESC")
    fun getBookmarkedCoursesOrderedByRating(): LiveData<List<CourseEntity>>

    @Query("SELECT id FROM courses")
    fun getBookmarkedCoursesIds(): LiveData<List<Int>>
}
