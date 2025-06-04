package com.example.mobile.View.utils

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.mobile.R
import java.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mobile.Model.GeminiView


@Composable
fun VLibras(descricao: String, isSystemInDarkTheme: Boolean) {

    var bgColor by remember { mutableStateOf("") }
    var fontColor by remember { mutableStateOf("") }

    if (isSystemInDarkTheme()){
        bgColor = "#0A1217"
        fontColor = "#FFFFFF"
    } else {
        bgColor = "#F1FBFF"
        fontColor = "#000000"
    }
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
                            padding: 10px;
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
                    <p id="text">$descricao</p>
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

@Composable
fun datePicker(context: Context): String {
    // State para armazenar a data selecionada
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val dateState = remember { mutableStateOf("") }

    // Abrir o DatePickerDialog quando o usuário clica no botão
    val datePickerDialog =
        DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            dateState.value = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year, month, day
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Data: ${dateState.value}",
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Selecionar Data")
        }
    }
    return dateState.value.toString()
}

@Composable
fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.popBackStack()},
        Modifier.padding(30.dp)
    ) {
       Icon(
           imageVector = Icons.AutoMirrored.Filled.ArrowBack,
           contentDescription = "Navigate Back",
           tint = MaterialTheme.colorScheme.primary
       )
    }
}

@Composable
fun ImageCarousel(navController: NavController, autorId: String, ids: List<String>, images: List<Bitmap>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(ids.size) {
            index ->
            CarouselItem(navController = navController, autorId = autorId, obraId = ids[index], obraImage = images[index])
        }
    }
}

@Composable
fun CarouselItem(navController: NavController, autorId: String, obraId: String, obraImage: Bitmap, ) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(150.dp)
            .clickable {
                navController.navigate("ObraGenerica/$autorId/$obraId")
            }
    ) {
        val painter = remember { BitmapPainter(obraImage.asImageBitmap()) }
        Image(
            painter = painter,
            contentDescription = null, // Decorative image
            contentScale = ContentScale.Crop, // Cropped to fill the container
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Round the image corners
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(geminiView: GeminiView) {
    // Estado das mensagens (uma lista de pares: a mensagem e se é do usuário ou não)
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() }
    // Estado do texto da mensagem
    val textState = remember { mutableStateOf("") }
    // Resposta do Gemini do ViewModel
    val geminiResponse by geminiView.resposta.collectAsState()

    // Função de envio da mensagem
    fun onMessageSent() {
        if (textState.value.isNotBlank()) {
            messages.add(textState.value to true) // Adiciona mensagem do usuário
            geminiView.callGemini(textState.value) // Chama o Gemini

            textState.value = "" // Limpa o campo de texto
        }
    }

    // Adiciona a resposta do Gemini à lista de mensagens
    LaunchedEffect(geminiResponse) {
        messages.add("Olá! Me pergunte alguma curiosidade!" to false)

        if (geminiResponse.isNotBlank()) {
            messages.add(geminiResponse to false) // Adiciona a resposta do bot
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Exibe as mensagens no chat
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(messages.size) { index ->
                val (msg, isUser) = messages[index]
                if (isUser) {
                    UserMessageCard(message = msg)
                } else {
                    ResponseCard(message = msg)
                }
            }
        }

        // Barra de input de mensagens
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = textState.value,
                onValueChange = { newText -> textState.value = newText },
                placeholder = { Text("Digite sua mensagem") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { onMessageSent() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ResponseCard(message: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_gemini_icon), // Ajuste para seu recurso
                contentDescription = "Gemini",
                modifier = Modifier.fillMaxSize(),
                tint = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun UserMessageCard(message: String) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}



