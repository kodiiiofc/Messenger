package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import androidx.compose.ui.graphics.painter.Painter
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ContactListItemModel(
    val id: String,
    val email: String,
    val name: String = "",
    val occupation: String = "",
    val address: String = "",
    val age: Int? = null,
    val avatar: String? = null
    )