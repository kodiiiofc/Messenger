package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ContactListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import javax.inject.Inject

class ContactsRepositoryImpl
@Inject constructor(private val supabaseClient: SupabaseClient) : ContactsRepository {

    override suspend fun updateContact(contact: ContactListItemModel): Boolean {
        /*TODO*/
        return false
    }

    override suspend fun updateContactList(searchEmailInput: String): List<ContactListItemModel> {
        return supabaseClient
            .from("public", "users")
            .select() {
                filter {
                    or {
                        ContactListItemModel::email ilike "%$searchEmailInput%"
                        ContactListItemModel::name ilike "%$searchEmailInput%"
                    }
                }
            }
            .decodeList<ContactListItemModel>()
    }
}