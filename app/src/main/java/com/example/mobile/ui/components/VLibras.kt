package com.example.mobile.ui.components

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mobile.util.poppinsFamily

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VLibras(
    descricao: String,
    darkTheme: Boolean = isSystemInDarkTheme(),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val bgColor = if (darkTheme) "#111827" else "#FFFFFF"
    val fontColor = if (darkTheme) "#F8FAFC" else "#0F172A"
    val safeDescricao = TextUtils.htmlEncode(descricao)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Accessible header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Accessibility,
                            contentDescription = "Libras",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Acessibilidade VLibras",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Avatar 3D em Libras",
                    fontFamily = poppinsFamily,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            ) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                allowContentAccess = true
                                allowFileAccess = true
                                javaScriptCanOpenWindowsAutomatically = true
                            }

                            webViewClient = object : WebViewClient() {
                                @Deprecated("Deprecated in Java")
                                override fun onReceivedError(
                                    view: WebView,
                                    errorCode: Int,
                                    description: String?,
                                    failingUrl: String?
                                ) {
                                    Log.e("VLibrasError", "Erro no WebView: $description")
                                }

                                override fun onPageFinished(view: WebView, url: String?) {
                                    Log.d("VLibras", "Página carregada com sucesso: $url")
                                }
                            }
                        }
                    },
                    update = { webView ->
                        val vlibrasHtml = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <meta charset="UTF-8">
                                <title>VLibras Demo</title>
                                <script src="https://vlibras.gov.br/app/vlibras-plugin.js"></script>
                                <style>
                                    body {
                                        background-color: $bgColor;
                                        color: $fontColor;
                                        font-family: 'Poppins', sans-serif;
                                        margin: 0;
                                        padding: 0;
                                        height: 100%;
                                    }
                                    p {
                                        text-align: center;
                                        padding: 12px;
                                        font-size: 13px;
                                        line-height: 1.5;
                                        overflow: auto;
                                        word-wrap: break-word;
                                        overflow-y: scroll;
                                    }
                                </style>
                            </head>
                            <body>
                                <div vw class="enabled">
                                    <div vw-access-button class="active"></div>
                                    <div vw-plugin-wrapper>
                                        <div class="vw-plugin-top-wrapper"></div>
                                    </div>
                                </div>
                                <p id="text">$safeDescricao</p>
                                <script>
                                    new window.VLibras.Widget('https://vlibras.gov.br/app');
                                </script>
                            </body>
                            </html>
                        """.trimIndent()

                        webView.loadDataWithBaseURL(null, vlibrasHtml, "text/html", "UTF-8", null)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
