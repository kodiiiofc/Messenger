package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class MessageModel @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String = UUID.randomUUID().toString(),
    val senderId: String,
    val receiverId: String,
    val message: String,
    val createdAt: String = LocalDateTime.now().toString(),
    val isRead: Boolean = false,
    val messageType: String = "text",
    val imageUrl: String? = null,
    val fileUrl: String? = null
    ) {



}
