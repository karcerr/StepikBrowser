package com.stepikbrowser.feature.home

import android.util.Log
import android.content.Context
import androidx.lifecycle.*
import com.stepikbrowser.domain.stepik.Course
import com.stepikbrowser.domain.stepik.CourseUseCase
import com.stepikbrowser.domain.stepik.StepikAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase,
    private val stepikAuthUseCase: StepikAuthUseCase
) : ViewModel() {
    var orderBy = "create_date"
    private var orderByAscending = true

    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>> get() = _courseList

    private val bookmarkedCourseIds = MutableLiveData<List<Int>>()
    init {
        courseUseCase.getBookmarkedCoursesIds().observeForever { ids ->
            bookmarkedCourseIds.postValue(ids)
        }
    }

    private var curPage = 1
    private var stepikAuthenticated = false

    fun authStepik() {
        if (stepikAuthenticated) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = stepikAuthUseCase.authApi()
            stepikAuthUseCase.saveAccessToken(result)
            stepikAuthenticated = true
        }
    }
    fun loadCourses(page: Int? = null) {
        page.let { curPage = it?: 1 }

        bookmarkedCourseIds.observeForever { bookmarkedIds ->
            viewModelScope.launch(Dispatchers.IO) {
                val result = if (_courseList.value.isNullOrEmpty())
                    courseUseCase.getCourses(curPage, getCurCourseOrder())?.map { course ->
                        course.copy(bookmarked = bookmarkedIds.contains(course.id))
                    }
                else
                    _courseList.value?.map {
                        course -> course.copy(bookmarked = bookmarkedIds.contains(course.id))
                    }

                Log.d("Courses List Logger", result?.size.toString() + " " + result?.map { it.id }.toString())
                _courseList.postValue(result)
            }
        }
    }
    fun changeCourseOrder(newOrderBy: String, ascending: Boolean) {
        if (orderBy == newOrderBy && orderByAscending == ascending) return
        curPage = 1
        orderBy = newOrderBy
        orderByAscending = ascending
        _courseList.postValue(emptyList())
        loadCourses()
    }
    fun loadNextCoursePage() {
        viewModelScope.launch(Dispatchers.IO) {
            curPage++
            val bookmarkedIds = bookmarkedCourseIds.value.orEmpty()
            val result = courseUseCase.getCourses(curPage, getCurCourseOrder())?.map { course ->
                course.copy(bookmarked = bookmarkedIds.contains(course.id))
            }
            Log.d("Courses List Logger", result?.size.toString() + result?.map{it.id}.toString())
            _courseList.postValue(_courseList.value.orEmpty() + (result?: emptyList()))
        }
    }
    fun bookmarkCourse(course: Course, isBookmarked: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            courseUseCase.bookmarkCourse(course, isBookmarked)
        }
    }

    override fun onCleared() {
        super.onCleared()
        bookmarkedCourseIds.removeObserver { }
        courseUseCase.getBookmarkedCoursesIds().removeObserver { }
    }
    private fun getCurCourseOrder() = if (orderByAscending) orderBy else "-$orderBy"
}