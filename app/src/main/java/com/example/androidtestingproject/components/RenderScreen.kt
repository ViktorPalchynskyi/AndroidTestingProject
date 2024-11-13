package com.example.androidtestingproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidtestingproject.ui.components.Content
import com.example.androidtestingproject.ui.components.SubmitButton
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.PaddingValues
import com.example.androidtestingproject.ui.components.NotificationSettingsScreen


@Composable
fun RenderScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        var username: String by remember { mutableStateOf("") }
        var usernameError: String? by remember { mutableStateOf(null) }
        var password: String by remember { mutableStateOf("") }
        var passwordError: String? by remember { mutableStateOf(null) }

        Content(
            text = username,
            onTextChange = { newText ->
                username = newText
                usernameError = when {
                    newText.length < 3 -> "Username must contain at least 3 characters"
                    newText.length > 30 -> "Username must contain 30 characters at most"
                    else -> null
                }
            },
            errorMessage = usernameError,
            labelName = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Content(
            text = password,
            onTextChange = { newText ->
                password = newText
                passwordError = when {
                    newText.length < 5 -> "Password must contain at least 5 characters"
                    newText.length > 30 -> "Password must contain 30 characters at most"
                    else -> null
                }
            },
            errorMessage = passwordError,
            labelName = "Password"
        )

        Spacer(modifier = Modifier.height(16.dp))

        val isFormValid = usernameError == null && passwordError == null && username.isNotEmpty() && password.isNotEmpty()

        SubmitButton(isFormValid)
        NotificationSettingsScreen()
    }
}