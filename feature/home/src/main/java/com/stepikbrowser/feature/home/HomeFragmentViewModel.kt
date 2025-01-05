package com.stepikbrowser.feature.home

import android.util.Log
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stepikbrowser.domain.stepik.Course
import com.stepikbrowser.domain.stepik.CourseUseCase
import com.stepikbrowser.domain.stepik.StepikAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase,
    private val stepikAuthUseCase: StepikAuthUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>> get() = _courseList

    private var curPage = 1
    private var stepikAuthenticated = false
    var curOrder: String? = null

    fun authStepik() {
        if (stepikAuthenticated) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = stepikAuthUseCase.authApi()
            stepikAuthUseCase.saveAccessToken(result)
            stepikAuthenticated = true
        }
    }
    fun loadCourses(page: Int? = null, orderBy: String? = null) {
        page.let { curPage = it?: 1 }
        curOrder = orderBy
        viewModelScope.launch(Dispatchers.IO) {
            val result = courseUseCase.getCourses(page, orderBy)
            Log.d("Courses List Logger", result?.size.toString() + result?.map{it.id}.toString())
            _courseList.postValue(result)
        }
    }
    fun loadNextCoursePage() {
        viewModelScope.launch(Dispatchers.IO) {
            curPage++
            val result = courseUseCase.getCourses(curPage, curOrder)
            Log.d("Courses List Logger", result?.size.toString() + result?.map{it.id}.toString())
            _courseList.postValue(_courseList.value.orEmpty() + (result?: emptyList()))
        }
    }
}