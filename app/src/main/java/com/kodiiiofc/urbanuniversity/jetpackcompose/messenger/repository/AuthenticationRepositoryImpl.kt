package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.content.Context
import android.util.Log
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.DeviceTokenModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import java.util.UUID
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthenticationRepository {

    override suspend fun signIn(email: String, password: String, context: Context): Boolean {
        return try {
            supabaseClient.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val session = supabaseClient.auth.currentSessionOrNull()

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

    override suspend fun resumeSession(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token", null)
        val refreshToken = sharedPreferences.getString("refresh_token", null)
        return try {
            if (accessToken != null && refreshToken != null) {
                supabaseClient.auth.importAuthToken(accessToken, refreshToken)
                supabaseClient.auth.currentSessionOrNull()!!.user!!.id
            } else null
        } catch (e: Exception) {
            Log.e("TAG", "resumeSession error: " + e.message)
            supabaseClient.auth.signOut()
            null
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            supabaseClient.auth.signUpWith(Email) {
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
            supabaseClient.auth.resetPasswordForEmail(email)
            true
        } catch (e: Exception) {
            Log.e("AUTH", "reset password error: " + e.message)
            false
        }
    }

    override suspend fun resetPasswordWithToken(token: String, password: String): Boolean {
        return try {
            supabaseClient.auth.verifyEmailOtp(
                type = OtpType.Email.RECOVERY,
                tokenHash = token
            )
            supabaseClient.auth.updateUser {
                this.password = password
            }
            true
        } catch (e: Exception) {
            Log.e("AUTH", "resetPasswordWithToken error: " + e.message)
            false
        }
    }

    override suspend fun updateTokensDb(token: String): Boolean {

        val currentSession = supabaseClient.auth.currentUserOrNull()
        if (currentSession != null) {
            val userId = currentSession.id
            val row = DeviceTokenModel(
                id = UUID.randomUUID().toString(),
                user_id = userId,
                device_token = token
            )
            return try {
                supabaseClient
                    .from( "device_tokens")
                    .insert(row)
                true
            } catch (e: Exception) {
                Log.e("MESSAGING", "updateTokensDb: " + e.message)
                false
            }
        }
        else return false
    }
}