package com.example.androidtestingproject

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class WebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url = intent.getStringExtra("URL") ?: "https://github.com/ViktorPalchynskyi"
            WebViewScreen(url = url)
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var webView: WebView? = null // Инициализация переменной webView

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            canGoBack = view?.canGoBack() ?: false
                            canGoForward = view?.canGoForward() ?: false
                        }
                    }
                    loadUrl(url)
                    webView = this
                }
            },
            modifier = Modifier.weight(1f),
            update = { webViewInstance ->
                webView = webViewInstance
                canGoBack = webViewInstance.canGoBack()
                canGoForward = webViewInstance.canGoForward()
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    webView?.goBack()
                },
                enabled = canGoBack
            ) {
                Text("Back")
            }

            Button(onClick = { webView?.reload() }) {
                Text("Reload")
            }

            Button(
                onClick = {
                    webView?.goForward()
                },
                enabled = canGoForward
            ) {
                Text("Forward")
            }
        }
    }
}
