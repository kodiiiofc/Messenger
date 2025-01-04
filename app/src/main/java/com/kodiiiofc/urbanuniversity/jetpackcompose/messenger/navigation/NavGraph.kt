package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.HomeScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ResetPasswordScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignInScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignUpScreen

@Composable
fun NavGraph(destination: String = Routes.HOME, bundle: Bundle? = null) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.SIGN_IN) {SignInScreen(navController)}
        composable(Routes.SIGN_UP) {SignUpScreen(navController)}
        composable(Routes.RESET_PASSWORD) {ResetPasswordScreen(navController, bundle = bundle)}
        composable(Routes.CHAT) {ResetPasswordScreen(navController, bundle = bundle)}
    }

}