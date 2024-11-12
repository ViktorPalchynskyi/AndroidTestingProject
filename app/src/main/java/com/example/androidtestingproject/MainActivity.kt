package com.example.androidtestingproject

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.androidtestingproject.ui.theme.AndroidTestingProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("Permissions", "Notification permission granted")
        } else {
            Log.d("Permissions", "Notification permission denied")
        }
    }

    private fun requestNotificationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM", "Device token: $token")
            } else {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission()
            }
        }

        enableEdgeToEdge()
        setContent {
            AndroidTestingProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RenderInputs(innerPadding = innerPadding)
                }
            }
        }
    }
}


@Composable
fun ErrorMessage(errorMessage: String?, modifier: Modifier = Modifier,) {
    Text(
        text = errorMessage!!,
        color = Color.Red,
        modifier = Modifier
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

        if (errorMessage != null) {
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
fun RenderInputs(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        var username by remember { mutableStateOf("") }
        var usernameError by remember { mutableStateOf<String?>(null) }
        var password by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf<String?>(null) }

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
    }
}