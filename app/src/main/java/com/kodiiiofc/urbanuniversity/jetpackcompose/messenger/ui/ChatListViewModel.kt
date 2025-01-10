package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ContactListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.ContactsRepository
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository.MessagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel
@Inject constructor(private val contactsRepository: ContactsRepository) : ViewModel() {

    private val _contacts = MutableStateFlow<List<ContactListItemModel>>(emptyList())
    val contacts: StateFlow<List<ContactListItemModel>> get() = _contacts

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
}