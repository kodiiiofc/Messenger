package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.util.Log
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Routes
import androidx.hilt.navigation.compose.hiltViewModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.data.AvatarResources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val email = viewModel.email.collectAsState(initial = "")
    val password = viewModel.password.collectAsState()

    val imageProfile = remember {
        mutableStateOf(AvatarResources.list.random())
    }

//    val email = remember {
//        mutableStateOf("")
//    }

//    val password = remember {
//        mutableStateOf("")
//    }

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
//                        email.value = it
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
//                        password.value = it
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
                            viewModel.onSignIn()
                            Toast.makeText(
                                navController.context,
                                "Успешный вход",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                ) {
                    Text("Войти")
                }

                TextButton(onClick = {/*TODO*/ }) {
                    Text("Забыл(а) пароль")
                }
            }
        }
    }
}