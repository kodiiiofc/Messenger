package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import androidx.compose.ui.graphics.painter.Painter
import java.util.UUID

data class ChatListItemModel(
    val userId: UUID,
    val name: String,
    val avatar: Painter? = null,
    val lastMessage: String = "Начните беседу! 👋",
    )