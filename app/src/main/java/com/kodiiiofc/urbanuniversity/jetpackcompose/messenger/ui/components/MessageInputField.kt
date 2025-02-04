package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R

@Composable
fun MessageInputField(
    inputText: MutableState<String>,
    onTrailingIconClick: () -> Unit,
    onLeadingIconClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            label = { Text("Введите сообщение") },
            modifier = Modifier.weight(1f),
            leadingIcon = {
                IconButton(
                    onClick = { onLeadingIconClick() }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_doc),
                        contentDescription = "Прикрепить документ"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onTrailingIconClick() }
                ) { Icon(Icons.Default.Send, "Отправить сообщение") }
            }
        )
    }
}