package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import java.util.UUID

interface MessagingRepository {
    suspend fun sendMessage(senderId: UUID, receiverId: UUID, message: String) : Boolean
    suspend fun getMessages(userId: UUID, otherUserId: UUID) : List<MessageModel>

}