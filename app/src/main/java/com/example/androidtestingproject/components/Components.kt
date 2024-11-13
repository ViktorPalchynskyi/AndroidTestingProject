package com.example.androidtestingproject.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androidtestingproject.WebViewActivity
import androidx.compose.material3.Switch
import androidx.compose.runtime.*

@Composable
fun ErrorMessage(errorMessage: String?, modifier: Modifier = Modifier) {
    Text(
        text = errorMessage ?: "",
        color = Color.Red,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        textAlign = TextAlign.Start
    )
}

@Composable
fun Content(
    text: String,
    onTextChange: (String) -> Unit,
    errorMessage: String?,
    labelName: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text(labelName) },
            modifier = Modifier.fillMaxWidth()
        )

        if (!errorMessage.isNullOrEmpty()) {
            ErrorMessage(errorMessage = errorMessage)
        }
    }
}

@Composable
fun SubmitButton(isFormValid: Boolean) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("URL", "https://github.com/ViktorPalchynskyi")
            context.startActivity(intent)
        },
        enabled = isFormValid,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFormValid) Color.Blue else Color.Gray
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Submit", color = Color.White)
    }
}

@Composable
fun NotificationSettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    var isNotificationsEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("notifications_enabled", true)) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Enable Notifications")
        Switch(
            checked = isNotificationsEnabled,
            onCheckedChange = { isChecked ->
                isNotificationsEnabled = isChecked
                sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply()
            }
        )
    }
}
