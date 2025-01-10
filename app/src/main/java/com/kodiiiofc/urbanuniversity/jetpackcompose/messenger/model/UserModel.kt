package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: String,
    val email: String,
    val name: String = "",
    val occupation: String = "",
    val address: String = "",
    val age: Int? = null,
    val avatar: String? = null
    )