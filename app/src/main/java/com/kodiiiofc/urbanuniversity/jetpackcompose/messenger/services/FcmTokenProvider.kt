package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FcmTokenProvider @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) {

    suspend fun getToken() =
        try {
            firebaseMessaging.token.await()
        } catch (e: Exception) {
            throw Exception("Failed to get FCM token: ", e)
        }

}