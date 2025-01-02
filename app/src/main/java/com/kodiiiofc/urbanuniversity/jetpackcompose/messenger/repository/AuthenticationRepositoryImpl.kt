package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.repository

import android.util.Log
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
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
            Log.e("AUTH", "signIn error: " + e.message)
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