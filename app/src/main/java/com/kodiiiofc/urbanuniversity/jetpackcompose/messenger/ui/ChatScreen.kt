package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.BuildConfig
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepositoryImpl
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleContact
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleUser
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.MessageInputField
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ChatScreen(
    navController: NavController,
    bundle: Bundle?,
) {

    val viewModel: ChatViewModel = ChatViewModel(
        messagingRepository = MessagingRepositoryImpl(
            supabaseClient =
            createSupabaseClient(
                supabaseUrl = BuildConfig.SUPABASE_URL,
                supabaseKey = BuildConfig.SUPABASE_ANON_KEY
            ) {

                install(Postgrest)
                install(Auth) {
                    flowType = FlowType.PKCE
                    scheme = "app"
                    host = "supabase.com"
                }
                install(Storage)
                install(Realtime)
            }
        )
    )

    val userId = bundle?.getString("userId")
    val receiverId = bundle?.getString("receiverId")
    val coroutineScope = rememberCoroutineScope()
    val messages = viewModel.messages.collectAsState()

    val inputText = remember {
        mutableStateOf("")
    }

    LaunchedEffect(userId, receiverId) {
        viewModel.onUpdateChat(
            userId = UUID.fromString(userId),
            otherUserId = UUID.fromString(receiverId)
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            LazyColumn(Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom) {
                items(messages.value) { message ->
                    Spacer(Modifier.height(2.dp))
                    if (message.sender_id == userId) {
                        Box(
                            Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) { ChatBubbleUser(message) }
                    } else if (message.receiver_id == userId) Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) { ChatBubbleContact(message) }
                    Spacer(Modifier.height(2.dp))
                }
            }
            MessageInputField(
                inputText = inputText,
                onTrailingIconClick = {
                    coroutineScope.launch {
                        viewModel.onSendMessage(
                            senderId = UUID.fromString(userId),
                            receiverId = UUID.fromString(receiverId),
                            textMessage = inputText.value
                        )
                        viewModel.onUpdateChat(
                            userId = UUID.fromString(userId),
                            otherUserId = UUID.fromString(receiverId)
                        )
                        inputText.value = ""
                    }
                },
                onLeadingIconClick = {
                    /*TODO*/
                },
            )
        }
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        navController = rememberNavController(),
        bundleOf(
            "userId" to "8f1d27ee-ef9d-498a-980f-03cce42a1619",
            "receiverId" to "35b5efb9-2c4c-4aba-ad3b-445ba7ff459f"
        )
    )
}