package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services.FcmTokenProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val fcmTokenProvider: FcmTokenProvider
) : ViewModel() {


    fun onResumeSession(context: Context, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val userId = authenticationRepository.resumeSession(context)
            if (userId != null) {
                authenticationRepository.updateTokensDb(fcmTokenProvider.getToken())
                onSuccess(userId)
            }
        }
    }

}