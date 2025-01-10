package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.broadcastFlow
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.realtime.selectAsFlow
import javax.inject.Inject

class ContactsRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : ContactsRepository {

    override suspend fun updateContact(contact: UserModel): Boolean {
        /*TODO*/
        return false
    }

    override suspend fun updateContactList(searchEmailInput: String): List<UserModel> {
        return supabaseClient
            .from("public", "users")
            .select() {
                filter {
                    or {
                        UserModel::email ilike "%$searchEmailInput%"
                        UserModel::name ilike "%$searchEmailInput%"
                    }
                }
            }
            .decodeList<UserModel>()
    }

    override suspend fun updateChatList(): List<ChatListItemModel> {

        return supabaseClient
            .from("public", "chats")
            .select {
                filter { ChatListItemModel::last_message neq "" }
            }
            .decodeList()
    }
}