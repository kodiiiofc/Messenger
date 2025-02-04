package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.data.AvatarResources
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val email = viewModel.email.collectAsState(initial = "")
    val password = viewModel.password.collectAsState()

    val imageProfile = remember {
        mutableIntStateOf(AvatarResources.list.random())
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painterResource(imageProfile.value),
                "Изображение профиля",
                modifier = Modifier
                    .size(120.dp)
                    .weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        viewModel.onEmailChange(it)
                    },
                    label = { Text("Электронная почта") },
                    leadingIcon = { Icon(Icons.Default.Email, "Электронная почта") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true
                )
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                    },
                    label = { Text("Пароль") },
                    leadingIcon = { Icon(Icons.Default.Lock, "Пароль") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(Modifier.size(24.dp))
                val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
                Button(
                    onClick = {
                        localSoftwareKeyboardController?.hide()
                        coroutineScope.launch {
                            if (viewModel.onSignIn(context = context)) {

                                Toast.makeText(
                                    navController.context,
                                    "Успешный вход",
                                    Toast.LENGTH_LONG
                                ).show()

                                navController.navigate(
                                    route = Screen.ChatList.getChatList(
                                        UUID.fromString("8f1d27ee-ef9d-498a-980f-03cce42a1619")
                                    )
                                )
                            }
                        }
                    },
                ) {
                    Text("Войти")
                }
                TextButton(onClick = {
                    coroutineScope.launch {
                        if (viewModel.onResetPassword()) {
                            Toast.makeText(
                                navController.context,
                                "Пароль успешно сброшен",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Что-то пошло не так",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }) {
                    Text("Забыл(а) пароль")
                }
            }
        }
    }
}