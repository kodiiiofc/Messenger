package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.Hint
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val email = viewModel.email.collectAsState(initial = "")
    val password = viewModel.password.collectAsState()
    var passwordConfirmation by remember {
        mutableStateOf("")
    }
    val passwordMatch by remember {
        derivedStateOf {
            password.value == passwordConfirmation
                    && password.value != ""
                    && passwordConfirmation != ""
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(Modifier.weight(1f)) {
                Hint(
                    headline = "Регистрация",
                    supportingText = "Введите электронную почту и пароль. \n" +
                            "Регистрируясь, вы соглашаетесь с условиями использования приложения."
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { viewModel.onEmailChange(it) },
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
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Пароль") },
                    leadingIcon = { Icon(Icons.Default.Lock, "Пароль") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )
                Spacer(Modifier.size(8.dp))
                OutlinedTextField(
                    value = passwordConfirmation,
                    onValueChange = { passwordConfirmation = it },
                    label = { Text("Подтверждение пароля") },
                    leadingIcon = {
                        Icon(
                            if (passwordMatch) Icons.Default.Check else Icons.Default.Close,
                            "Пароль"
                        )
                    },
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
                            viewModel.onSignUp()
                            snackbarHostState.showSnackbar(
                                message = "Регистрация успешна.",
                                duration = SnackbarDuration.Long
                            )
                        }
                    },
                    enabled = passwordMatch
                ) {
                    Text("Зарегистрироваться")
                }
                Spacer(Modifier.size(48.dp))
            }
        }
    }
}