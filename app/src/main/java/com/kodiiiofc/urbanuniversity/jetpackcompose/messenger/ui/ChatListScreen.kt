package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen() {

    val contacts = listOf(
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Vasya Pupkin",
            avatar = painterResource(AvatarResources.list[16]),
            lastMessage = "Привет, как дела? Есть планы на вечер?"
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Lesha",
            avatar = painterResource(AvatarResources.list[13]),
            lastMessage = "Что там по задачам? Мне кажется, что ты уже очень сильно затянул. Пора отдавать"
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Ruslan",
            avatar = painterResource(AvatarResources.list[12])
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Petr Perviy",
            lastMessage = "Я в Европу прорубил окно, а ты чего добился? Ты вообще никто, понял? А я император Российской Империи!"
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Ilya V",
            avatar = painterResource(AvatarResources.list[25]),
            lastMessage = "Я устал работать в этой компании, надо основывать свою"
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Anastasia Sh",
            avatar = painterResource(AvatarResources.list[7]),
            lastMessage = "Наша компания это просто кошмар, все время горят сроки, какой-то бред!!! Еще и задачи ставятся хрен пойми как..."
        ),
        ChatListItemModel(
            userId = UUID.randomUUID(),
            name = "Lyudmila",
            avatar = painterResource(AvatarResources.list[19])
        ),
    )

    val menuExpanded = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopBar(menuExpanded)
        },
        bottomBar = {

        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Spacer(Modifier.size(8.dp))
            LazyColumn(
                modifier = Modifier
            ) {
                items(contacts) {
                    ChatListItem(it)
                    Spacer(Modifier.size(8.dp))
                }
            }
        }


    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(menuExpanded: MutableState<Boolean>) {
    TopAppBar(
        title = { Text("Чаты") },
        actions = {
            Row {
                IconButton(
                    onClick = {/*TODO*/ }
                ) {
                    Icon(Icons.Default.AccountCircle, "Мой профиль")
                }
                IconButton(
                    onClick = { menuExpanded.value = true }
                ) {
                    Icon(Icons.Default.MoreVert, "Мой профиль")
                }
                DropdownMenu(
                    expanded = menuExpanded.value,
                    onDismissRequest = { menuExpanded.value = false }
                ) {
                    DropdownMenuItem(
                        text = { "О приложении" },
                        onClick = {/*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { "Выйти" },
                        onClick = { /*TODO navController.context.finish()*/ }
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ChatListScreenPreview() {
    ChatListScreen()
}

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