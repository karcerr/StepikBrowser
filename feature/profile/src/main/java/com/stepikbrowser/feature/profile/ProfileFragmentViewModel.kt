package com.stepikbrowser.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stepikbrowser.domain.auth.AuthenticatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val courseUseCase: AuthenticatedUseCase
): ViewModel() {
    private val _authStatus = MutableLiveData(true)
    val authStatus: LiveData<Boolean> get() = _authStatus

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email


    init {
        viewModelScope.launch {
            _email.postValue(getUserEmail())
        }
    }
    fun logout() {
        courseUseCase.logout()
        _authStatus.postValue(false)
    }
    private suspend fun getUserEmail(): String {
        return courseUseCase.getCurrentUser()?.email?: "Error"
    }
}