package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import java.util.UUID

const val USER_ID = "userId"
const val OTHER_USER_ID = "otherUserId"

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object SignIn : Screen("signIn")
    data object SignUp : Screen("signUp")
    data object ResetPassword : Screen("resetPassword")
    data object ChatList : Screen("chatList/{$USER_ID}") {
        fun getChatList(
            userId: UUID
        ) : String {
            return "chat/$userId"
        }
    }
    data object Chat : Screen("chat/{$USER_ID}/{$OTHER_USER_ID}") {
        fun getChat(
            userId: UUID,
            otherUserID: UUID
        ): String {
            return "chat/$userId/$otherUserID"
        }
    }
}