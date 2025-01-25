package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.NavGraph
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.services.FcmTokenProvider
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.theme.MessengerTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    @Inject
    lateinit var messagingRepository : MessagingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        messagingRepository.realtimeDB()
        CoroutineScope(Dispatchers.IO).launch {
            messagingRepository.getMessages()
        }

        setContent {
            MessengerTheme {
                NavGraph()
            }
        }
    }
}