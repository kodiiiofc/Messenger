package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.HomeScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignUpScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) { HomeScreen(navController) }
//        composable(Routes.SIGN_IN) {SignInScreen(navController)}
        composable(Routes.SIGN_UP) {SignUpScreen(navController)}
    }

}