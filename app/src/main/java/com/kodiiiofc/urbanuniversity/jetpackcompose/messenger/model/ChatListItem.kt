package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatListItem(
    val user: UserModel,
    val otherUser: UserModel,
    val lastMessage: MessageModel
)