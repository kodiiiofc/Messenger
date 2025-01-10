package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import java.io.File
import java.util.UUID
import javax.inject.Inject

class MessagingRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : MessagingRepository {

    override suspend fun sendMessage(message: MessageModel): Boolean {
        return try {
            supabaseClient
                .from("public", "messages")
                .insert(message)
            getMessages(
                UUID.fromString(message.sender_id),
                UUID.fromString(message.receiver_id)
            )
            true
        } catch (e: Exception) {
            Log.e("MESSAGING", "sendMessage: " + e.message)
            false
        }
    }

    override suspend fun getMessages(userId: UUID, otherUserId: UUID): List<MessageModel> {
        val messages = supabaseClient
            .from("public", "messages")
            .select {
                filter {
                    or {
                        and {
                            MessageModel::sender_id eq userId
                            MessageModel::receiver_id eq otherUserId
                        }
                        and {
                            MessageModel::sender_id eq otherUserId
                            MessageModel::receiver_id eq userId
                        }
                    }
                }
                order(column = "created_at", order = Order.ASCENDING)
            }
            .decodeList<MessageModel>()

        if (messages.isNotEmpty()) updateChatList(messages.last())

        return messages
    }

    override suspend fun updateChatList(
        message: MessageModel
    ): Boolean {
        return try {
            val user = supabaseClient
                .from("public", "users")
                .select { filter { UserModel::id eq message.sender_id } }
                .decodeList<UserModel>()
                .first()

            val otherUser = supabaseClient
                .from("public", "users")
                .select { filter { UserModel::id eq message.receiver_id } }
                .decodeList<UserModel>()
                .first()

            val chatListItemModel = ChatListItemModel(
                owner_id = user.id,
                user_id = otherUser.id,
                name = otherUser.name,
                last_message = message.message,
                avatar = otherUser.avatar
            )

            val isNewChat = supabaseClient
                .from("public", "chats")
                .select {
                    filter {
                        and {
                            ChatListItemModel::owner_id eq message.sender_id
                            ChatListItemModel::user_id eq message.receiver_id
                            ChatListItemModel::last_message neq ""
                        }
                    }
                }
                .decodeList<ChatListItemModel>()
                .isEmpty()

            if (isNewChat) {
                supabaseClient
                    .from("public", "chats")
                    .insert(chatListItemModel)
            } else {
                supabaseClient
                    .from("public", "chats")
                    .update(chatListItemModel) {
                        filter {
                            and {
                                ChatListItemModel::owner_id eq message.sender_id
                                ChatListItemModel::user_id eq message.receiver_id
                            }
                        }
                    }
            }
            true
        } catch (e: Exception) {
            Log.e("MESSAGING", "updateChatList: " + e.message)
            false
        }
    }

    override suspend fun uploadImage(message: MessageModel, file: File): MessageModel {
        val bucket = supabaseClient.storage["images"]
        val filePath = "${message.sender_id}/${message.receiver_id}/${message.id}" /*todo*/

        bucket.upload(
            path = filePath,
            file = file
        )

        return MessageModel(
            id = message.id,
            sender_id = message.sender_id,
            receiver_id = message.receiver_id,
            created_at = message.created_at,
            message = message.message.ifBlank { "Изображение" },
            is_read = false,
            message_type = "image",
            file_url = bucket.publicUrl(filePath)
        )
    }
}