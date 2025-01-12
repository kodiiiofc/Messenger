package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatListItem(
    val user: UserModel,
    val otherUser: UserModel,
    val lastMessage: MessageModel
) {
    override fun equals(other: Any?): Boolean {
        return if (other is ChatListItem)
         (this.user == other.user) && (this.otherUser == other.otherUser)
        else false
    }

    override fun hashCode(): Int {
        var result = user.hashCode()
        result = 31 * result + otherUser.hashCode()
        result = 31 * result + lastMessage.hashCode()
        return result
    }
}