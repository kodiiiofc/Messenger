package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.data.AvatarResources
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ChatListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.model.ContactListItemModel
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatListScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ChatScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.HomeScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.ResetPasswordScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignInScreen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.SignUpScreen
import java.util.UUID

@Composable
fun NavGraph(destination: String = Screen.Home.route, bundle: Bundle? = null) {

    val chatContacts = listOf(
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


    val contacts = listOf(
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Vasya Pupkin",
            avatar = painterResource(AvatarResources.list[16]),
        ),
        ContactListItemModel(
            userId = UUID.fromString("35b5efb9-2c4c-4aba-ad3b-445ba7ff459f"),
            name = "Lesha",
            avatar = painterResource(AvatarResources.list[13]),
        ),
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Ruslan",
            avatar = painterResource(AvatarResources.list[12])
        ),
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Petr Perviy",
        ),
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Ilya V",
            avatar = painterResource(AvatarResources.list[25]),
        ),
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Anastasia Sh",
            avatar = painterResource(AvatarResources.list[7]),
        ),
        ContactListItemModel(
            userId = UUID.randomUUID(),
            name = "Lyudmila",
            avatar = painterResource(AvatarResources.list[19])
        ),
    )


    val navController = rememberNavController()

    // TODO: Решить проблему с навигацией!

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.SignIn.route) { SignInScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                navController,
                bundle = bundle
            )
        }
        composable(
            route = Screen.ChatList.route,
            arguments = listOf(navArgument(USER_ID) {type = NavType.StringType})
        ) {
            ChatListScreen(
                navController,
                chatContacts,
                contacts,
                UUID.fromString(it.arguments?.getString(USER_ID).toString())
            )
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument(USER_ID) { type = NavType.StringType },
                navArgument(OTHER_USER_ID) { type = NavType.StringType })
        ) {
            it.arguments
            ChatScreen(navController, bundle = it.arguments)
        }
    }

}