package com.stepikbrowser.feature.bookmarks

import androidx.lifecycle.*
import com.stepikbrowser.domain.stepik.Course
import com.stepikbrowser.domain.stepik.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksFragmentViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase
) : ViewModel() {
    private val _orderBy = MutableLiveData("title")
    val orderBy: LiveData<String> get() = _orderBy

    val bookmarkedCourses: LiveData<List<Course>> = _orderBy.switchMap { order ->
        courseUseCase.getBookmarkedCourses(order)
    }

    fun changeOrderBy(newOrder: String) {
        _orderBy.value = newOrder
    }
    fun bookmarkCourse(course: Course, isBookmarked: Boolean?) {
        viewModelScope.launch(Dispatchers.IO) {
            courseUseCase.bookmarkCourse(course, isBookmarked)
        }
    }
}