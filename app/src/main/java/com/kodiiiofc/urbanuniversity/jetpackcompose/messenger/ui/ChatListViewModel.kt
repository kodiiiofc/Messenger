package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ChatListViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    val messages: StateFlow<List<MessageModel>> get() = messagingRepository.messages

    var userId: String? = null

    private val _contacts = MutableStateFlow<List<UserModel>>(emptyList())
    val contacts: StateFlow<List<UserModel>> get() = _contacts

    val chats: StateFlow<List<ChatListItem>> = messages.map { messages ->
        messages.map { message ->
            ChatListItem(
                user = messagingRepository.getUserById(userId!!),
                otherUser = messagingRepository.getUserById(
                    if (message.sender_id != userId)
                        message.sender_id
                    else message.receiver_id
                ),
                lastMessage = message
            )
        }.reversed().distinctBy { it.otherUser }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

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

    fun realtimeDb() = viewModelScope.launch {
        messagingRepository.getMessages()
        messagingRepository.realtimeDB(this)
    }

}