package com.example.androidtestingproject

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.androidtestingproject.ui.theme.AndroidTestingProjectTheme
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.androidtestingproject.ui.RenderScreen
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
                    RenderScreen(innerPadding = innerPadding)
                }
            }
        }
    }
}