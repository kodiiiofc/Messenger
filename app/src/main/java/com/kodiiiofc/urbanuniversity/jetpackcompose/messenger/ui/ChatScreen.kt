package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleContact
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleUser
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavController,
    bundle: Bundle?,
    viewModel: ChatViewModel = hiltViewModel()
) {


    val userId = bundle?.getString("userId")
    val receiverId = bundle?.getString("receiverId")
    val coroutineScope = rememberCoroutineScope()
    val messages = viewModel.messages.collectAsState()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(messages.value) { message ->
                    if (message.senderId == userId) {
                        ChatBubbleUser(message)
                    } else ChatBubbleContact(message)
                }
            }
        }
    }
}