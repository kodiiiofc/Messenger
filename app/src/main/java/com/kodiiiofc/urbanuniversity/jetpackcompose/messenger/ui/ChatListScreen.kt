package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.R
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.UserModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.TabItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ChatListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.ContactListItem
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components.UserSearch
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ChatListScreen(
    navController: NavController,
    userId: UUID,
    viewModel: ChatListViewModel = hiltViewModel()
) {

    val menuExpanded = remember {
        mutableStateOf(false)
    }

    val tabList = listOf(
        TabItem(
            title = "Чаты",
            selectedIcon = ImageVector.vectorResource(R.drawable.chats_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.chats),
        ),
        TabItem(
            title = "Контакты",
            selectedIcon = ImageVector.vectorResource(R.drawable.contacts_selected),
            unselectedIcon = ImageVector.vectorResource(R.drawable.contacts),
        )
    )

    val selectedTab = remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabList.size
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedTab.intValue) {
        pagerState.animateScrollToPage(selectedTab.intValue)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTab.intValue = pagerState.currentPage
        }
    }

    Scaffold(
        topBar = {
            TopBar(menuExpanded)
        },
        bottomBar = {
            TabRow(
                selectedTabIndex = selectedTab.intValue
            ) {
                tabList.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == selectedTab.intValue,
                        onClick = {
                            selectedTab.intValue = index
                        },
                        text = { Text(text = tab.title) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTab.intValue) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title,
                            )
                        }
                    )
                }
            }
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val contacts = viewModel.contacts.collectAsState()

                    val chats = viewModel.chats.collectAsState()

                    LaunchedEffect(viewModel) {
                        viewModel.onUpdateChatList()
                    }

                    when (index) {
                        1 -> {
                            val searchInput = remember {
                                mutableStateOf("")
                            }
                            Column {
                                UserSearch(searchInput) {
                                    coroutineScope.launch {
                                        viewModel.onUpdateContactsList(searchInput.value)
                                    }
                                }
                            }
                            ContactsTabContent(contacts.value) {
                                navController.navigate(
                                    Screen.Chat.getChat(
                                        userId = userId,
                                        otherUserID = UUID.fromString(contacts.value[it].id)
                                    )
                                )
                            }
                        }

                        else -> ChatTabContent(chats.value) {
                            navController.navigate(
                                Screen.Chat.getChat(
                                    userId = userId,
                                    otherUserID = UUID.fromString(chats.value[it].user_id)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(menuExpanded: MutableState<Boolean>) {
    val context = LocalContext.current
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
                        text = { Text("О приложении") },
                        onClick = { /*TODO*/ }
                    )
                    DropdownMenuItem(
                        text = { Text("Выйти") },
                        onClick = {
                            if (context is Activity) {
                                context.finishAffinity()
                            }
                        }
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ChatListScreenPreview() {

//    val chatContacts = listOf(
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Vasya Pupkin",
//            avatar = painterResource(AvatarResources.list[16]),
//            lastMessage = "Привет, как дела? Есть планы на вечер?"
//        ),
//        ChatListItemModel(
//            userId = UUID.fromString("35b5efb9-2c4c-4aba-ad3b-445ba7ff459f"),
//            name = "Lesha",
//            avatar = painterResource(AvatarResources.list[13]),
//            lastMessage = "Что там по задачам? Мне кажется, что ты уже очень сильно затянул. Пора отдавать"
//        ),
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Ruslan",
//            avatar = painterResource(AvatarResources.list[12])
//        ),
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Petr Perviy",
//            lastMessage = "Я в Европу прорубил окно, а ты чего добился? Ты вообще никто, понял? А я император Российской Империи!"
//        ),
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Ilya V",
//            avatar = painterResource(AvatarResources.list[25]),
//            lastMessage = "Я устал работать в этой компании, надо основывать свою"
//        ),
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Anastasia Sh",
//            avatar = painterResource(AvatarResources.list[7]),
//            lastMessage = "Наша компания это просто кошмар, все время горят сроки, какой-то бред!!! Еще и задачи ставятся хрен пойми как..."
//        ),
//        ChatListItemModel(
//            userId = UUID.randomUUID(),
//            name = "Lyudmila",
//            avatar = painterResource(AvatarResources.list[19])
//        ),
//    )

    ChatListScreen(
        rememberNavController(),
        UUID.fromString("8f1d27ee-ef9d-498a-980f-03cce42a1619")
    )
}

@Composable
fun ChatTabContent(contacts: List<ChatListItemModel>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
    ) {
        itemsIndexed(contacts) { index, item ->
            ChatListItem(item) {
                onItemClick(index)
            }
            Spacer(Modifier.size(8.dp))
        }
    }
}

@Composable
fun ContactsTabContent(contacts: List<UserModel>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
    ) {
        itemsIndexed(contacts) { index, item ->
            ContactListItem(item) { onItemClick(index) }
            Spacer(Modifier.size(8.dp))
        }
    }
}