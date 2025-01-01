package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger

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