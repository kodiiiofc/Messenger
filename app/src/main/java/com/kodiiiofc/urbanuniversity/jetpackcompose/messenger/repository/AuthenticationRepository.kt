package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

interface AuthenticationRepository {

    suspend fun signIn(
        email: String,
        password: String
    ) : Boolean

    suspend fun signUp(
        email: String,
        password: String
    ) : Boolean
}