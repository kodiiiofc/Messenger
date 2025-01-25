package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model

import kotlinx.serialization.Serializable

@Serializable
class DeviceTokenModel (
    val id: String,
    val user_id: String,
    val device_token: String
)

