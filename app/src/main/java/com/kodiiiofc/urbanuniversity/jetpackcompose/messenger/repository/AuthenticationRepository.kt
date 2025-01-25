package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.content.Context

interface AuthenticationRepository {

    suspend fun signIn(
        email: String,
        password: String,
        context: Context
    ): Boolean

    suspend fun signUp(
        email: String,
        password: String
    ): Boolean

    suspend fun resetPassword(email: String): Boolean
    suspend fun resetPasswordWithToken(token: String, password: String): Boolean
    suspend fun saveSession(accessToken: String, refreshToken: String, context: Context)
    suspend fun resumeSession(context: Context) : String?
    suspend fun updateTokensDb(token: String) : Boolean

}