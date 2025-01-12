package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.util.UUID

interface MessagingRepository {

    val usersCache: List<UserModel>

    suspend fun sendMessage(message: MessageModel) : Boolean
    suspend fun getMessages(userId: UUID, otherUserId: UUID) : List<MessageModel>
    suspend fun uploadImage(message: MessageModel, file: File) : MessageModel
    suspend fun subscribeToMessages() : Flow<List<MessageModel>>
    suspend fun updateContactList(searchInput: String): List<UserModel>
    suspend fun getUserById(userId: String): UserModel
}