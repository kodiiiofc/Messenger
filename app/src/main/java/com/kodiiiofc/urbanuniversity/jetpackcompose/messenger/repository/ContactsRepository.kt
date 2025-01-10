package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel

interface ContactsRepository {
    suspend fun updateContact(contact: UserModel) : Boolean
    suspend fun updateContactList(searchEmailInput: String) : List<UserModel>
    suspend fun updateChatList() : List<ChatListItemModel>
}