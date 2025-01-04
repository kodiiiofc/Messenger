package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.Hint
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    bundle: Bundle?,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    val token = bundle!!.getString("token")!!
    val coroutineScope = rememberCoroutineScope()

    val password = viewModel.password.collectAsState()

    val showPassoword = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f)) {
            Hint(
                headline = "Восстановление пароля",
                supportingText = "Укажите новый пароль для учетной записи"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                label = { Text("Новый пароль") },
                leadingIcon = { Icon(Icons.Default.Lock, "Пароль") },
                trailingIcon = {
                    IconButton(
                        onClick = { showPassoword.value = !showPassoword.value },
                        content = {
                            Icon(
                                painterResource(if (showPassoword.value) R.drawable.ic_visibility_off else R.drawable.ic_visibility),
                                "Показать пароль"
                            )
                        })
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (showPassoword.value) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(Modifier.size(24.dp))

            Button(onClick =
            {
                coroutineScope.launch {
                    if (viewModel.onResetPassword(token)) {
                        Toast.makeText(
                            navController.context,
                            "Пароль успешно обновлен",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }) {
                Icon(Icons.Default.Send, "Обновить пароль")
                Spacer(Modifier.width(8.dp))
                Text("Обновить пароль")
            }
        }
    }
}