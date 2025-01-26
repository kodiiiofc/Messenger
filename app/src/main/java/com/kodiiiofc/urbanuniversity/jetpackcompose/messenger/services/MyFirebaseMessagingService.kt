package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.NotificationModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.AuthenticationRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService
    : FirebaseMessagingService() {

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        CoroutineScope(Dispatchers.IO).launch {
            authenticationRepository.updateTokensDb(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)

        CoroutineScope(Dispatchers.IO).launch {
            val userId = authenticationRepository.resumeSession(this@MyFirebaseMessagingService)


            var senderUser: UserModel? = null
            val sender = remoteMessage.data.get("sender")
            if (sender != null) {
                senderUser = Json.decodeFromString<UserModel>(sender.slice(1..sender.length - 2))
            }

            val notification = NotificationModel(
                title = remoteMessage.notification?.title ?: "Уведомление",
                body = remoteMessage.notification?.body ?: "Вам пришло уведомление",
                avatar = senderUser!!.avatar
            )

            showNotification(notification, userId, senderUser.id)

            Log.d("TAG", "onMessageReceived data: ${remoteMessage.data}")
            Log.d("TAG", "onMessageReceived notification: ${remoteMessage.notification}")

        }
    }

    private fun showNotification(notification: NotificationModel, userId: String?, otherUserId: String) {

        val bundle: Bundle =  bundleOf(
            "userId" to userId,
            "otherUserId" to otherUserId
        )

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            if (userId != null) {
                putExtras(bundle)
            }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "message"
        val imageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(notification.avatar)
            .target { drawable ->
                val bitmap = (drawable as BitmapDrawable).bitmap

                Log.d("TAG", "showNotification drawable: $drawable")
                Log.d("TAG", "showNotification bitmap: $bitmap")

                val notificationBuilder = NotificationCompat
                    .Builder(this, channelId)
                    .setContentTitle(notification.title)
                    .setContentText(notification.body)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(notification.icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        "Сообщения",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationManager.createNotificationChannel(channel)
                }
                notificationManager.notify(0, notificationBuilder.build())
            }.build()

        imageLoader.enqueue(request)
    }
}
