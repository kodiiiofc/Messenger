package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> get() = _messages

    private val _contacts = MutableStateFlow<List<UserModel>>(emptyList())
    val contacts: StateFlow<List<UserModel>> get() = _contacts

    private val _chats = MutableStateFlow<List<ChatListItem>>(emptyList())
    val chats: StateFlow<List<ChatListItem>> get() = _chats

    fun onUpdateContactsList(searchInput: String): Boolean {
        return try {
            viewModelScope.launch {
                _contacts.value = messagingRepository.updateContactList(searchInput)
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", "onUpdateContactsList error:" + e.message)
            false
        }
    }

    fun onUpdateChatList(userId: String) = viewModelScope.launch {

        _chats.value = messages.value.map { message ->

            ChatListItem(
                user = messagingRepository.getUserById(userId),
                otherUser = messagingRepository.getUserById(if (message.sender_id != userId) message.sender_id else message.receiver_id),
                lastMessage = message
            )

        }.reversed().distinctBy { it.otherUser }

    }

    fun onSubscribeToMessages() {
        viewModelScope.launch {
            messagingRepository.subscribeToMessages().collect {
                _messages.value = it
            }
        }
    }

}