package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String, context: Context): Boolean {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val session = auth.currentSessionOrNull()

            if (session != null) {
                saveSession(
                    accessToken = session.accessToken,
                    refreshToken = session.refreshToken,
                    context = context
                )
            }

            true
        } catch (e: Exception) {
            Log.e("AUTH", "signIn error: " + e.message)
            false
        }
    }

    override suspend fun saveSession(accessToken: String, refreshToken: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }

    override suspend fun resumeSession(context: Context) : String? {
        val sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null)
        val refreshToken = sharedPreferences.getString("refresh_token", null)
        return if (accessToken != null && refreshToken != null) {
            auth.importAuthToken(accessToken, refreshToken)
            auth.currentSessionOrNull()!!.user!!.id
        } else null
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("AUTH", "signUp error: " + e.message)
            false
        }
    }

    override suspend fun resetPassword(email: String): Boolean {
        return try {
            auth.resetPasswordForEmail(email)
            true
        } catch (e: Exception) {
            Log.e("AUTH", "reset password error: " + e.message)
            false
        }
    }

    override suspend fun resetPasswordWithToken(token: String, password: String): Boolean {
        return try {
            auth.verifyEmailOtp(
                type = OtpType.Email.RECOVERY,
                tokenHash = token
            )
            auth.updateUser {
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("AUTH", "resetPasswordWithToken error: " + e.message)
            false
        }
    }
}