package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import kotlinx.coroutines.flow.StateFlow
import java.io.File

interface MessagingRepository {

    val messages: StateFlow<List<MessageModel>>
    val usersCache: List<UserModel>
    val chats: StateFlow<List<ChatListItem>>

    suspend fun sendMessage(message: MessageModel): Boolean
    suspend fun getMessages()
    fun realtimeDB()
    suspend fun uploadImage(message: MessageModel, file: File): MessageModel
    suspend fun updateContactList(searchInput: String): List<UserModel>
    suspend fun getUserById(userId: String): UserModel
}