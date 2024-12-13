package com.stepikbrowser.feature.auth

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stepikbrowser.domain.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationFragmentViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    private val _authStatus = MutableLiveData<Boolean>()
    val authStatus: LiveData<Boolean> get() = _authStatus


    fun authenticate(email: String, password: String, repeatedPassword: String?, isRegister: Boolean) {
        val validationResult = if (isRegister) {
            validateRegister(email, password, repeatedPassword)
        } else {
            validateLogin(email, password)
        }

        if (validationResult != null) {
            _errorState.value = validationResult
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isRegister)
                    registerFirebase(email, password)
                else
                    loginFirebase(email, password)
                _authStatus.postValue(true)
            }
            catch (e: Exception) {
                _errorState.postValue(e.message)
            }
        }


    }
    private fun validateLogin(email: String, password: String): String? {
        return when {
            email.isBlank() -> getString(context, R.string.empty_email_error)
            password.isBlank() -> getString(context, R.string.empty_password_error)
            else -> null
        }
    }
    private suspend fun loginFirebase(email: String, password: String) {
        authUseCase.login(email, password)
    }
    private suspend fun registerFirebase(email: String, password: String) {
        authUseCase.register(email, password)
    }

    private fun validateRegister(email: String, password: String, repeatedPassword: String?): String? {
        return when {
            email.isBlank() -> getString(context, R.string.empty_email_error)
            password.isBlank() -> getString(context, R.string.empty_password_error)
            password != repeatedPassword -> getString(context, R.string.passwords_dont_match_error)
            else -> null
        }
    }

}