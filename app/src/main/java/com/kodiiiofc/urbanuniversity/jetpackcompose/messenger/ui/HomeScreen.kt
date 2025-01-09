package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                onClick = {
                    navController.navigate(Screen.SignIn.route)
                }
            ) {
                Text("Войти в учетную запись")
            }

            Spacer(Modifier.size(8.dp))

            Button(
                onClick = { navController.navigate(Screen.SignUp.route) }
            ) {
                Text("Регистрация")
            }

        }
    }
}