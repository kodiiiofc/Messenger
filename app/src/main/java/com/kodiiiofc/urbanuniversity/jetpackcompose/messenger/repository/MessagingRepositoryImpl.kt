package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class MessagingRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : MessagingRepository {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    override val messages: StateFlow<List<MessageModel>> get() = _messages

    override suspend fun sendMessage(message: MessageModel): Boolean {
        return try {
            supabaseClient
                .from("public", "messages")
                .insert(message)
            true
        } catch (e: Exception) {
            Log.e("MESSAGING", "sendMessage: " + e.message)
            false
        }
    }

    override suspend fun getMessages() {
        _messages.value = supabaseClient
            .from("public", "messages")
            .select {
                order(column = "created_at", order = Order.ASCENDING)
            }
            .decodeList<MessageModel>()
    }

    override suspend fun realtimeDB(coroutineScope: CoroutineScope) {
        try {
            val channel = supabaseClient.channel("test")

            val dataFlow = channel.postgresChangeFlow<PostgresAction>(
                schema = "public"
            )

            dataFlow.onEach {
                when (it) {
                    is PostgresAction.Delete -> {
                        val stringifiedData = it.oldRecord.toString()
                        val data = Json.decodeFromString<MessageModel>(stringifiedData)
                        _messages.value = _messages.value.filter { message ->
                            message.id != data.id
                        }
                        Log.d("TAG", "realtimeDb: data deleted ${messages.value}")
                    }

                    is PostgresAction.Insert -> {
                        val stringifiedData = it.record.toString()
                        val data = Json.decodeFromString<MessageModel>(stringifiedData)
                        _messages.value = _messages.value.map { message ->
                            if (message.id == data.id) {
                                data
                            } else message
                        }
                        Log.d("TAG", "realtimeDb: data inserted  ${messages.value}")
                    }

                    is PostgresAction.Select -> {
                        Log.d("TAG", "realtimeDb: data selected  ${messages.value}")
                    }

                    is PostgresAction.Update -> {
                        val stringifiedData = it.record.toString()
                        val data = Json.decodeFromString<MessageModel>(stringifiedData)
                        Log.d("TAG", "realtimeDb: data updated $data")
                        _messages.value = _messages.value.map { message ->
                            if (message.id == data.id) {
                                data
                            } else message
                        }
                    }
                }
            }.launchIn(coroutineScope)

            channel.subscribe()

        } catch (e: Exception) {
            Log.e("TAG", "subscribeToData error: " + e.message)
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