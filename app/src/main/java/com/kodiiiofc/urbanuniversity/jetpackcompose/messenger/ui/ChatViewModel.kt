package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> get() = _messages

    suspend fun onSendMessage(
        senderId: UUID,
        receiverId: UUID,
        textMessage: String,
        file: File? = null
    ): Boolean {

        var message = MessageModel(
            sender_id = senderId.toString(),
            receiver_id = receiverId.toString(),
            message = textMessage
        )

        if (file != null) message = messagingRepository.uploadImage(message, file)

        return messagingRepository.sendMessage(message)
    }

    fun onUpdateChat(userId: UUID, otherUserId: UUID) {


//        viewModelScope.launch {
//            _messages.value = messagingRepository.getMessages(userId, otherUserId)
//        }
    }

    fun onSubscribeToMessages() {
        viewModelScope.launch {
            messagingRepository.subscribeToMessages().collect {
                _messages.value = it
            }
        }
    }

    suspend fun createFileFromUri(context: Context, uri: Uri): File? = withContext(Dispatchers.IO) {
        return@withContext try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                throw Exception("Не удалось открыть InputStream для Uri: $uri")
            }
            val file = File.createTempFile("temp", null, context.cacheDir)
            inputStream.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}