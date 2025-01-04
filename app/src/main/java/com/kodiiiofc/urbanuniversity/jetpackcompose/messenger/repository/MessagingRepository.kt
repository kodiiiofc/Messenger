package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import java.util.UUID

interface MessagingRepository {
    suspend fun sendMessage(message: MessageModel) : Boolean
    suspend fun getMessages(userId: UUID, otherUserId: UUID) : List<MessageModel>
}