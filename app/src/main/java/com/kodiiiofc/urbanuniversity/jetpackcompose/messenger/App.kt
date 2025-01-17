package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger

import android.app.Application
import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services.FcmTokenProvider
import dagger.hilt.android.HiltAndroidApp
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    @Inject
    lateinit var messagingRepository : MessagingRepository

    @Inject
    lateinit var fcmTokenProvider : FcmTokenProvider

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            val token = fcmTokenProvider.getToken()
            Log.d("FCM", "fcmTokenProvider.getToken(): $token ")
        }

        messagingRepository.realtimeDB()

        CoroutineScope(Dispatchers.IO).launch {
            messagingRepository.getMessages()
        }
    }
}