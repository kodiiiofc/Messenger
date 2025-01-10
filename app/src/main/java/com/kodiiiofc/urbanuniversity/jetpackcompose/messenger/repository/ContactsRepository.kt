package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ContactListItemModel

interface ContactsRepository {
    suspend fun updateContact(contact: ContactListItemModel) : Boolean
    suspend fun updateContactList(searchEmailInput: String) : List<ContactListItemModel>
}