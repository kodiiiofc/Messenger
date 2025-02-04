package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel
@Inject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    var userId: String? = null
    private val _contacts = MutableStateFlow<List<UserModel>>(emptyList())
    val contacts: StateFlow<List<UserModel>> get() = _contacts
    val chats: StateFlow<List<ChatListItem>> = messagingRepository.chats

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

    fun getMessages() = viewModelScope.launch {
        messagingRepository.getMessages()
    }
}