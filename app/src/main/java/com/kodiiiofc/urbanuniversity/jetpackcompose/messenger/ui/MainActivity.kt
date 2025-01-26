package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.NavGraph
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.theme.MessengerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

                val userId = intent.extras?.getString("userId")
                val otherUserId = intent.extras?.getString("otherUserId")

                Log.d("TAG", "onCreate: ${userId} \n $otherUserId")
                if (userId != null && otherUserId != null) {
                    NavGraph(
                        Screen.Chat.getChat(
                        userId = UUID.fromString(userId),
                        otherUserID = UUID.fromString(otherUserId)
                    ))
                }

                NavGraph()
            }
        }
    }
}