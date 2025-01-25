package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services.FcmTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val fcmTokenProvider: FcmTokenProvider
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

    suspend fun onSignIn(context: Context): Boolean {
        val isSignIn = authenticationRepository.signIn(
            email = _email.value,
            password = _password.value,
            context = context
        )
        if (isSignIn) {
            viewModelScope.launch {
                authenticationRepository.updateTokensDb(fcmTokenProvider.getToken())
            }
        }
        return isSignIn
    }

    suspend fun onResetPassword() : Boolean {
        return authenticationRepository.resetPassword(email = _email.value)
    }
}