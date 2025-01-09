package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.os.bundleOf
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.NavGraph
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.navigation.Screen
import com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.theme.MessengerTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordActivity : ComponentActivity() {

    @Inject
    lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessengerTheme {
                val data: Uri? = intent.data
                if (data != null) {
                    val token = data.getQueryParameter("token")
                    if (token != null) {
                        NavGraph(
                            Screen.ResetPassword.route, bundleOf(
                                "token" to token
                            )
                        )
                    } else {
                        Toast.makeText(this, "Invalid link", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
