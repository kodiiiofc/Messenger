package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services.FcmTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val fcmTokenProvider: FcmTokenProvider
) : ViewModel() {


    fun onResumeSession(context: Context, onSuccess: (String) -> Unit, onDenied: () -> Unit) {
        viewModelScope.launch {
            val userId = authenticationRepository.resumeSession(context)
            if (userId != null) {
                authenticationRepository.updateTokensDb(fcmTokenProvider.getToken())
                onSuccess(userId)
            } else {
                onDenied()
            }
        }
    }

}