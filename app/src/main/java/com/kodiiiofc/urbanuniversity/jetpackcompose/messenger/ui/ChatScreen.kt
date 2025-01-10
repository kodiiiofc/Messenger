package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.OTHER_USER_ID
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.USER_ID
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleContact
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatBubbleUser
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.MessageInputField
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

@Composable
fun ChatScreen(
    navController: NavController,
    bundle: Bundle?,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val userId = bundle?.getString(USER_ID)
    val otherUserId = bundle?.getString(OTHER_USER_ID)

    Log.d("TAG", "ChatScreen: $userId")
    Log.d("TAG", "ChatScreen: $otherUserId")

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val messages = viewModel.messages.collectAsState()

    val inputText = remember {
        mutableStateOf("")
    }

    val fileUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri ->
        fileUri.value = uri
    }

    LaunchedEffect(userId, otherUserId) {
        viewModel.onUpdateChat(
            userId = UUID.fromString(userId),
            otherUserId = UUID.fromString(otherUserId)
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            LazyColumn(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                itemsIndexed(messages.value) { index, message ->
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
                        val file = if (fileUri.value != null) viewModel.createFileFromUri(context, fileUri.value!!) else null

                        viewModel.onSendMessage(
                            senderId = UUID.fromString(userId),
                            receiverId = UUID.fromString(otherUserId),
                            textMessage = inputText.value,
                            file = file
                        )
                        viewModel.onUpdateChat(
                            userId = UUID.fromString(userId),
                            otherUserId = UUID.fromString(otherUserId)
                        )
                        inputText.value = ""
                    }
                },
                onLeadingIconClick = {
                    filePickerLauncher.launch("image/*")
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
            "otherUserId" to "35b5efb9-2c4c-4aba-ad3b-445ba7ff459f"
        )
    )
}