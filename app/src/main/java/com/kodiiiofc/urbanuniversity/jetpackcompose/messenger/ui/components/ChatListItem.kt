package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.data.AvatarResources
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import java.util.UUID


@Composable
fun ChatListItem(chatListItemModel: ChatListItemModel) {
    Row(
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .height(88.dp)
            .padding(16.dp, 12.dp)
    ) {
        Image(
            painter = chatListItemModel.avatar ?: painterResource(R.drawable.avatar_empty),
            contentDescription = chatListItemModel.name,
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.size(16.dp))

        Column(Modifier.fillMaxWidth()) {
            Text(text = chatListItemModel.name, fontSize = 16.sp, lineHeight = 24.sp)
            Text(
                text = chatListItemModel.lastMessage,
                maxLines = 2,
                softWrap = true,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(
    showBackground = true,
)
@Composable
fun ChatListItemPreview() {

    val item = ChatListItemModel(
        userId = UUID.randomUUID(),
        name = "Vasya Pupkin",
        avatar = painterResource(AvatarResources.list[16])
    )

    ChatListItem(item)
}