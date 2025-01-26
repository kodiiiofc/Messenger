package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatListScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.HomeScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ResetPasswordScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignInScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignUpScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.StartScreen
import java.util.UUID

@Composable
fun NavGraph(destination: String = Screen.Start.route, bundle: Bundle? = null) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(Screen.Start.route) { StartScreen(navController) }
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