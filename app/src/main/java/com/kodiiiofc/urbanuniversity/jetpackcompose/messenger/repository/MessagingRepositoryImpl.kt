package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.realtime.realtime
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class MessagingRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : MessagingRepository {

    override suspend fun sendMessage(message: MessageModel): Boolean {
        return try {
            supabaseClient
                .from("messaging", "messaging")
                .insert(message)
            true
        } catch (e: Exception) {
            Log.e("MESSAGING", "sendMessage: " + e.message)
            false
        }
    }

    override suspend fun getMessages(userId: UUID, otherUserId: UUID): List<MessageModel> {
        return supabaseClient
            .from("messages", "messages")
            .select() {
                filter {
                    MessageModel::senderId eq userId
                    MessageModel::receiverId eq otherUserId
                }
                order(column = "created_at", order = Order.ASCENDING)
            }
            .decodeList<MessageModel>()
    }
}