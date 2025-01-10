package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel
@Inject constructor(private val contactsRepository: ContactsRepository) : ViewModel() {

    private val _contacts = MutableStateFlow<List<UserModel>>(emptyList())
    val contacts: StateFlow<List<UserModel>> get() = _contacts

    private val _chats = MutableStateFlow<List<ChatListItemModel>>(emptyList())
    val chats: StateFlow<List<ChatListItemModel>> get() = _chats

    fun onUpdateContactsList(searchInput: String): Boolean {
        return try {
            viewModelScope.launch {
                _contacts.value = contactsRepository.updateContactList(searchInput)
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", "onUpdateContactsList error:" + e.message)
            false
        }
    }

//    todo: fun onUpdateContact()

    fun onUpdateChatList(): Boolean {
        return try {
            viewModelScope.launch {
                _chats.value = contactsRepository.updateChatList()
            }
            true
        } catch (e: Exception) {
            Log.e("TAG", "onUpdateChatList error: " + e.message)
            false
        }
    }
}