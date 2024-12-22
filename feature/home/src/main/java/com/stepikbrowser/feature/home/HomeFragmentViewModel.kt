package com.stepikbrowser.feature.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stepikbrowser.domain.courses.Course
import com.stepikbrowser.domain.courses.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _courseList = MutableLiveData<List<Course>>()
    val courseList: LiveData<List<Course>> get() = _courseList

    fun loadCourses(page: Int, orderBy: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Courses Logger", "requesting courses")
            val result = courseUseCase.getCourses(page, orderBy)
            Log.d("Courses Logger", result.toString())
        }
    }
}