package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    val messages: StateFlow<List<MessageModel>> get() = messagingRepository.messages

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

    fun getMessages() = viewModelScope.launch {
        messagingRepository.getMessages()
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