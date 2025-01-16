package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class MessagingRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : MessagingRepository {

    private var realtimeChannel: RealtimeChannel? = null

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    override val messages: StateFlow<List<MessageModel>> get() = _messages

    private var userId = supabaseClient.auth.currentSessionOrNull()?.user?.id

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override val chats: StateFlow<List<ChatListItem>> =
        combine(_messages) { messages ->
            updateUserId()
            messages[0].map { message ->
                ChatListItem(
                    user = getUserById(userId!!),
                    otherUser = getUserById(
                        if (message.sender_id != userId)
                            message.sender_id
                        else message.receiver_id
                    ),
                    lastMessage = message
                )
            }.sortedByDescending { it.lastMessage.created_at }.distinctBy {
                it.otherUser
            }.also { Log.d("TAG", "updatingChats : $it") }
        }.catch { e ->
            Log.e("TAG", "Error in chats flow: $e")
        }.stateIn(repositoryScope, SharingStarted.Lazily, emptyList())

    private fun updateUserId() {
        userId = supabaseClient.auth.currentSessionOrNull()?.user?.id
    }

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

    private fun parseOldRecord(stringifiedData: String): String {
        return stringifiedData.drop(7).dropLast(2)
    }

    override fun realtimeDB() {
        repositoryScope.launch {
            realtimeChannel = supabaseClient.channel("test")
            val dataFlow = realtimeChannel!!.postgresChangeFlow<PostgresAction>(
                schema = "public"
            )

            dataFlow.onEach {
                when (it) {
                    is PostgresAction.Delete -> {
                        val stringifiedData = it.oldRecord.toString()
                        val deletedMessageId = parseOldRecord(stringifiedData)
                        _messages.value = _messages.value.filter { message ->
                            message.id != deletedMessageId
                        }
                        Log.d("TAG", "realtimeDb: data deleted ${stringifiedData}")
                        Log.d("TAG", "realtimeDb: data deleted ${deletedMessageId}")
                        Log.d("TAG", "realtimeDb: data deleted ${messages.value}")

                    }

                    is PostgresAction.Insert -> {
                        val stringifiedData = it.record.toString()
                        val data = Json.decodeFromString<MessageModel>(stringifiedData)
                        _messages.value = _messages.value.toMutableList().apply { add(data) }
                        Log.d("TAG", "realtimeDb: data inserted  ${data}")
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
            }.launchIn(repositoryScope)

            realtimeChannel!!.subscribe()
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