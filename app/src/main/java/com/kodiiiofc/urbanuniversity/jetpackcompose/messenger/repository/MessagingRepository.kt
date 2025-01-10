package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import java.io.File
import java.util.UUID

interface MessagingRepository {
    suspend fun sendMessage(message: MessageModel) : Boolean
    suspend fun getMessages(userId: UUID, otherUserId: UUID) : List<MessageModel>
    suspend fun updateChatList(message: MessageModel) : Boolean
    suspend fun uploadImage(message: MessageModel, file: File) : MessageModel
}