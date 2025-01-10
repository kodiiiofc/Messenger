package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class MessageModel(
    val id: String = UUID.randomUUID().toString(),
    val sender_id: String,
    val receiver_id: String,
    val message: String,
    val created_at: String = LocalDateTime.now().toString(),
    val is_read: Boolean = false,
    val message_type: String = "text", // "image", "document"
    val file_url: String? = null
)
