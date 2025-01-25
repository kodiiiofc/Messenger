package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R

data class NotificationModel(
    val title: String,
    val body: String,
    val avatar: String?,
    val icon: Int = R.drawable.chats
)
