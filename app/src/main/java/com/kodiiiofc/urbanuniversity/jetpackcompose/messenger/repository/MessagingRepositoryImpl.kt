package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import java.util.UUID
import javax.inject.Inject

class MessagingRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : MessagingRepository {

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

    override suspend fun getMessages(userId: UUID, otherUserId: UUID): List<MessageModel> {
        return supabaseClient
            .from("public", "messages")
            .select {
                filter {
                    MessageModel::sender_id isIn listOf(userId, otherUserId)
                    MessageModel::receiver_id isIn listOf(userId, otherUserId)
                }
                order(column = "created_at", order = Order.ASCENDING)
            }
            .decodeList<MessageModel>()
    }
}