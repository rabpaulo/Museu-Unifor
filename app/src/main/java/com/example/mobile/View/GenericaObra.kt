package com.example.mobile.View

import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.Models.GeminiViewModel
import com.example.mobile.View.utils.ChatScreen
import com.example.mobile.View.utils.VLibras
import com.example.mobile.View.utils.base64ToBitmap
import com.example.mobile.View.utils.dynamicSpacerHeight
import com.example.mobile.View.utils.poppinsFamily
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

lateinit var textToSpeech: TextToSpeech

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObrasGenerica(navController: NavController, idAutor : String?, idObra: String?, gemini: GeminiViewModel) {
    val db = FirebaseFirestore.getInstance()

    val nomeAutor = remember { mutableStateOf("") }
    val nomeObra = remember { mutableStateOf("") }
    val descricaoObra = remember { mutableStateOf("") }
    val dataObra = remember { mutableStateOf("") }
    val imageObra = remember { mutableStateOf("") }

    var decodedBitmap by remember { mutableStateOf<Bitmap?>(null) } // For storing the decoded image

    var textState = remember { mutableStateOf("") }
    val context = LocalContext.current

    idAutor?.let {
        idObra?.let {
            db.collection("autor")
                .document(idAutor)
                .collection("obras")
                .document(idObra)
                .get()
                .addOnSuccessListener {
                    document ->
                    nomeAutor.value = document.getString("autor").orEmpty()
                    nomeObra.value = document.getString("nome").orEmpty()
                    dataObra.value = document.getString("data").orEmpty()
                    descricaoObra.value = document.getString("descricao").orEmpty()
                    imageObra.value = document.getString("image").orEmpty()

                    val base64String = imageObra.value
                    if (base64String.isNotEmpty()) {
                        decodedBitmap = base64ToBitmap(base64String)
                    }
                }
                .addOnFailureListener {
                    e ->
                    Log.e("FirestoreError", "Error fetching author data", e)
                }
        }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Back button nao funciona se tiver uma Column scrollavel: Linha 64
        // Tive q adicionar uma Row com o BackButton dentro
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        nomeObra.value,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Set transparent background
                )
            )
        }

        // Dynamic image display
        if (decodedBitmap != null) {
            Image(
                painter = BitmapPainter(decodedBitmap!!.asImageBitmap()),
                contentDescription = "Obra Image",
                Modifier.size(150.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.pfp), // Placeholder
                contentDescription = "Placeholder Image",
                Modifier.size(150.dp)
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Autor: " + nomeAutor.value,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = dataObra.value,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.padding(top = 4.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        Row {
            textToSpeech = TextToSpeech(
                context,
                TextToSpeech.OnInitListener { status ->
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech?.language = Locale("pt", "BR")
                    }
                }
            )
            ExtendedFloatingActionButton(
                onClick = { textToSpeech?.speak(descricaoObra.value, QUEUE_FLUSH, null, null) },
                icon = { Icon(Icons.Filled.PlayArrow, "Extended floating action button.") },
                text = { Text(text = "Ler Texto",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal)  },
            )

            Spacer(Modifier.width(16.dp))
        }
        Row(Modifier.height(370.dp).fillMaxWidth()) {
            VLibras(descricaoObra.value, isSystemInDarkTheme())
        }

        ExtendedFloatingActionButton(
            onClick = {navController.navigate("AutorGenerica/$idAutor")},
            icon = { Icon(Icons.Filled.Person, "Extended floating action button.") },
            text = { Text(text = "Visitar Autor" + '\n' + nomeAutor.value) },
        )

            Text(
                "Converse com Gemini:",
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(10.dp),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
            )

            Row(modifier = Modifier
                .height(dynamicSpacerHeight(0.5f))
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))) {
                ChatScreen(gemini)
            }
        }
    }
