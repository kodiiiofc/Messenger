package com.kodiiiofc.urbanuniversity.jetpackcompose.messenger.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Hint(headline: String, supportingText: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp, 12.dp)

    ) {
        Text(
            text = "Регистрация",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = supportingText,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HintPreview() {
    Hint(
        headline = "Регистрация",
        supportingText = "Введите электронную почту и пароль. \n" +
                "Регистрируясь, вы соглашаетесь с условиями использования приложения.",
    )
}