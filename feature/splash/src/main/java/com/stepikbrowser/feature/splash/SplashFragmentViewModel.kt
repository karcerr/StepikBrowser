package com.stepikbrowser.feature.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stepikbrowser.domain.auth.CheckUserLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val checkUserLoginStatusUseCase: CheckUserLoginStatusUseCase
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean?>(null)
    val isLoggedIn: LiveData<Boolean?> get() = _isLoggedIn

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val result =  checkUserLoginStatusUseCase.invoke()
        _isLoggedIn.postValue(result)
    }
}