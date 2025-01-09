package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import androidx.lifecycle.ViewModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _password = MutableStateFlow("")
    val password = _password

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    suspend fun onResetPassword(token: String) : Boolean {
        return authenticationRepository.resetPasswordWithToken(token, password.value)
    }
}