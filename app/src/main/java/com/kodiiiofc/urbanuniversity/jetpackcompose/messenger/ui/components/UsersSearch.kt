package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserSearch(value: MutableState<String>, onSearchIconClick: () -> Unit) {
    OutlinedTextField(
        value = value.value,
        onValueChange = {value.value = it},
        modifier = Modifier.fillMaxWidth().padding(4.dp, 8.dp),
        label = {Text("Найти пользователя")},
        trailingIcon = {
            IconButton(
                onClick = { onSearchIconClick() }
            ) {
                Icon(Icons.Default.Search, "Найти")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UserSearchPreview() {
    val value = remember {
        mutableStateOf("")
    }
    UserSearch(value) {}
}