package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import java.util.UUID

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(context) {
        viewModel.onResumeSession(
            context = context,
            onSuccess = { userId ->
                navController.navigate(Screen.ChatList.getChatList(UUID.fromString(userId)))
            },
            onDenied = {
                navController.navigate(Screen.Home.route)
            }
        )
    }

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Логотип"
        )
        Spacer(Modifier.height(24.dp))
        CircularProgressIndicator()
    }
}



