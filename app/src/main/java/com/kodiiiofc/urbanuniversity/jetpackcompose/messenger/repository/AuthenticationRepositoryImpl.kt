package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("AUTH", "signIn error: " + e.message, )
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("AUTH", "signUp error: " + e.message, )
            false
        }
    }
}