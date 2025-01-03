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

    fun authStepik() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = stepikAuthUseCase.authApi()
            stepikAuthUseCase.saveAccessToken(result)
        }
    }
    fun loadCourses(page: Int? = null, orderBy: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = courseUseCase.getCourses(page, orderBy)
            Log.d("Courses List Logger", result?.size.toString() + result?.map{it.id}.toString())
            _courseList.postValue(result)
        }
    }

}