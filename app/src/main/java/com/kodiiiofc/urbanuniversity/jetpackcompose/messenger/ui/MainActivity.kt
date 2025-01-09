package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.NavGraph
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.theme.MessengerTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MessengerTheme {

                NavGraph()

            }
        }
    }
}