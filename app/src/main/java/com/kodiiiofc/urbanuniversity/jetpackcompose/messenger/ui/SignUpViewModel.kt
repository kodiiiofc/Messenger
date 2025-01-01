package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignUp() {
        viewModelScope.launch {
            authenticationRepository.signUp(
                email = _email.value,
                password = _password.value
            )
        }
    }
}