package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger

import android.app.Application
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
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

    override fun onCreate() {
        super.onCreate()
        messagingRepository.realtimeDB()

        CoroutineScope(Dispatchers.Default).launch {
            messagingRepository.getMessages()
        }
    }
}