package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItem


@Composable
fun ChatListItem(chatListItem: ChatListItem, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp, 0.dp)
            .clickable { onItemClick() }
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
            .height(88.dp)
            .padding(16.dp, 12.dp)
    ) {
        Image(
            painter = /* todo chatListItemModel.avatar ?: */painterResource(R.drawable.avatar_empty),
            contentDescription = chatListItem.otherUser.name,
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.size(16.dp))

        Column(Modifier.fillMaxWidth()) {
            Text(text = chatListItem.otherUser.name, fontSize = 16.sp, lineHeight = 24.sp)
            Text(
                text = chatListItem.lastMessage.message,
                maxLines = 2,
                softWrap = true,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}