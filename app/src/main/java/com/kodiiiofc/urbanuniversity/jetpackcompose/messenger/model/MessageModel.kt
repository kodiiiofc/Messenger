package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MessageModel(
    val id: String = UUID.randomUUID().toString(),
    val senderId: String,
    val receiverId: String,
    val message: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean = false,
    val messageType: String = "text",
    val imageUrl: String? = null,
    val fileUrl: String? = null
    ) {



}
