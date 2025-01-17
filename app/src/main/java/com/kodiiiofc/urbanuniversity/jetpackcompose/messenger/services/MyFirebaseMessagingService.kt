package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Обработка данных уведомления
        remoteMessage.data.let { data ->
            Log.d("FCM", "Message data: $data")
            val title = data["title"]
            val body = data["body"]
            if (title != null && body != null) {
                showNotification(title, body)
            }
        }

        // Обработка уведомления, если оно пришло, когда приложение в foreground
        remoteMessage.notification?.let { notification ->
            val title = notification.title
            val body = notification.body
            if (title != null && body != null) {
                showNotification(title, body)
            }
        }
    }

    private fun showNotification(title: String, body: String) {
        // Реализация отображения уведомления (см. следующий раздел)
    }
}
