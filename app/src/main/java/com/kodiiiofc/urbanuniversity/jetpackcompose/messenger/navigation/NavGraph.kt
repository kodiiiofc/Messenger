package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.data.AvatarResources
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatListScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.HomeScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ResetPasswordScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignInScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignUpScreen
import java.util.UUID

@Composable
fun NavGraph(destination: String = Screen.Home.route, bundle: Bundle? = null) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.SignIn.route) { SignInScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                navController,
                bundle = bundle
            )
        }
        composable(
            route = Screen.ChatList.route,
            arguments = listOf(navArgument(USER_ID) {type = NavType.StringType})
        ) {
            ChatListScreen(
                navController,
                UUID.fromString(it.arguments?.getString(USER_ID).toString())
            )
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument(USER_ID) { type = NavType.StringType },
                navArgument(OTHER_USER_ID) { type = NavType.StringType })
        ) {
            ChatScreen(navController, bundle = it.arguments)
        }
    }

}