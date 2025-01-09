package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class ChatViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> get() = _messages

    suspend fun onSendMessage(senderId: UUID, receiverId: UUID, textMessage: String) : Boolean {
        val message = MessageModel(
            senderId = senderId.toString(),
            receiverId = receiverId.toString(),
            message = textMessage
        )

        return messagingRepository.sendMessage(message)
    }

    fun onUpdateChat(userId: UUID, otherUserId: UUID) {
        viewModelScope.launch {
            _messages.value = messagingRepository.getMessages(userId, otherUserId)
        }
    }
}