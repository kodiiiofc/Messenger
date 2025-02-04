package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.MessageModel

@Composable
fun ChatBubbleUser(message: MessageModel) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(
            topStart = 28.dp,
            topEnd = 28.dp,
            bottomStart = 28.dp,
            bottomEnd = 0.dp
        ),
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column (
            modifier = Modifier
                .padding(24.dp, 16.dp)
                .widthIn(
                    min = 0.dp,
                    max = 270.dp
                )
        ) {
            if (message.file_url != null) {
                DisplayImage(message.file_url)
                Spacer(Modifier.size(8.dp))
            }
            Text(
                text = message.message,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ChatBubbleContact(message: MessageModel) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 28.dp,
            bottomStart = 28.dp,
            bottomEnd = 28.dp
        ),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp, 16.dp)
                .widthIn(
                    min = 0.dp,
                    max = 270.dp
                ),
        ) {
            if (message.file_url != null) {
                DisplayImage(message.file_url)
                Spacer(Modifier.size(8.dp))
            }
            Text(
                text = message.message,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun DisplayImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Изображение",
        modifier = Modifier.size(240.dp)
    )
}