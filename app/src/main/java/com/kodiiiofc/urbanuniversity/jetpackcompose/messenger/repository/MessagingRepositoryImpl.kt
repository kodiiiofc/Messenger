package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.flow.Flow
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

        return messages
    }

    override suspend fun subscribeToMessages(): Flow<List<MessageModel>> {

        val channel = supabaseClient.channel("messages_channel")

        val flow = channel.postgresListDataFlow(
            schema = "public",
            table = "messages",
            primaryKey = MessageModel::id,
            filter = null
        )

        channel.subscribe()

        return flow
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

    override suspend fun updateContactList(searchInput: String): List<UserModel> {
        return supabaseClient
            .from("public", "users")
            .select() {
                filter {
                    or {
                        UserModel::email ilike "%$searchInput%"
                        UserModel::name ilike "%$searchInput%"
                    }
                }
            }
            .decodeList<UserModel>()
    }

    override suspend fun getUserById(userId: String): UserModel {

        var currentUser = usersCache.find { user -> (user.id == userId) }
        if (currentUser == null) {
            currentUser = supabaseClient
                .from("public", "users")
                .select {
                    filter { UserModel::id eq userId }
                }.decodeList<UserModel>().first()
            usersCache.add(currentUser)
        }
        return currentUser

    }

    override val usersCache: MutableList<UserModel> = mutableListOf()
}