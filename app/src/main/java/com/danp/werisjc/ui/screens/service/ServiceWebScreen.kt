package com.danp.werisjc.ui.screens.service

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.danp.werisjc.ui.theme.Celeste

@Composable
fun ServiceWebScreen(
    serviceName: String,
    serviceUrl: String
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // 🌟 Encabezado igual que ServiceDetailScreen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Celeste)
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = serviceName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // WebView
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    loadUrl(serviceUrl)
                    settings.javaScriptEnabled = true
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

