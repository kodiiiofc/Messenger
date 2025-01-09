package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Box(
            modifier = Modifier
                .padding(24.dp, 16.dp)
                .widthIn(
                    min = 0.dp,
                    max = 270.dp
                ),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = message.message,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubbleUserPreview() {

    val text = "Купи хлеба и цветов для мамы, мы завтра едем к ней, не забыл?"

    val message = MessageModel(

        senderId = "0000dsa",
        receiverId = "dsadsa",
        message = text,


        )

    ChatBubbleUser(message)
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
        Box(
            modifier = Modifier
                .padding(24.dp, 16.dp)
                .widthIn(
                    min = 0.dp,
                    max = 270.dp
                ),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = message.message,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubbleContactPreview() {

    val text =
        "Тут вот очень длинное сообщение в несколько строк, может даже в три или в четыре строки"

    val message = MessageModel(

        senderId = "0000dsa",
        receiverId = "dsadsa",
        message = text,


        )


    ChatBubbleContact(message)
}