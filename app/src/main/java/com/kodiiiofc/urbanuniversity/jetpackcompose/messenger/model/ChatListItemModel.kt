package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatListItemModel(
    val owner_id: String,
    val user_id: String,
    val name: String,
    val last_message: String = "Начните беседу! 👋",
    val avatar: String? = null,
)