package com.example.mobile.View

import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mobile.Model.GeminiView

import com.example.mobile.Utils.ChatScreen
import com.example.mobile.Utils.ImageCarousel
import com.example.mobile.Utils.VLibras
import com.example.mobile.Utils.base64ToBitmap
import com.example.mobile.Utils.dynamicSpacerHeight
import com.example.mobile.Utils.poppinsFamily
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutorGenerica(navController: NavController, id: String?, gemini: GeminiView) {

    val db = FirebaseFirestore.getInstance()

    val authorName = remember { mutableStateOf("") }
    val authorDescription = remember { mutableStateOf("") }
    val authorData = remember { mutableStateOf("") }
    val autorImage = remember { mutableStateOf("") }

    val obrasImages = remember { mutableStateOf<List<Bitmap>>(emptyList()) }
    val obrasIds = remember { mutableStateOf<List<String>>(emptyList()) }

    var decodedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    id?.let {
        db.collection("autor").document(it)
            .get()
            .addOnSuccessListener { document ->
                authorName.value = document.getString("nome") ?: ""
                authorData.value = document.getString("data") ?: ""
                authorDescription.value = document.getString("descricao") ?: ""
                autorImage.value = document.getString("image") ?: ""

                val base64String = autorImage.value
                if (base64String.isNotEmpty()) {
                    decodedBitmap = base64ToBitmap(base64String)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching author data", e)
            }

        db.collection("autor")
            .document(it)
            .collection("obras")
            .get()
            .addOnSuccessListener { obrasSnapshot ->
                val idsList = mutableListOf<String>()
                val bitmapsList = mutableListOf<Bitmap>()

                obrasSnapshot.documents.forEach { document ->
                    val obraImageBase64 = document.getString("image")
                    val obraId = document.id // Fetch the document ID
                    val bitmap = if (!obraImageBase64.isNullOrEmpty()) base64ToBitmap(obraImageBase64) else null

                    // Store the ID and Bitmap separately
                    if (bitmap != null) {
                        idsList.add(obraId)
                        bitmapsList.add(bitmap)
                    }
                }

                // Update the state with the lists
                obrasIds.value = idsList
                obrasImages.value = bitmapsList
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching obras", e)
            }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        authorName.value,
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

        Text(
            text = authorData.value,
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.padding(top = 4.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        Row {
            textToSpeech = TextToSpeech(
                context,
                TextToSpeech.OnInitListener{
                        status ->
                    if(status != TextToSpeech.ERROR){
                        textToSpeech?.language = Locale("pt", "BR")
                    }
                }
            )

            ExtendedFloatingActionButton(
                onClick = { textToSpeech?.speak(authorDescription.value, QUEUE_FLUSH, null, null) },
                icon = { Icon(Icons.Filled.PlayArrow, "Extended floating action button.") },
                text = { Text(text = "Ler Texto") },
            )



            Spacer(Modifier.width(16.dp))

        }

        Row(Modifier.height(370.dp)){
            VLibras(authorDescription.value, isSystemInDarkTheme())
        }

        Text("Obras do autor:",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold)
        Box(
            Modifier.padding(
                start = 30.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 30.dp

            )
        ) {

            obrasImages.value.let { obraBitmaps ->
                obrasIds.value.let { obraIds ->
                    if (obraBitmaps.isNotEmpty() && obraIds.isNotEmpty() && obraBitmaps.size == obraIds.size) {
                        ImageCarousel(
                            navController,
                            autorId = id + "",
                            images = obraBitmaps,
                            ids = obraIds
                        )
                    } else {
                        Log.e("FirestoreError", "Mismatch in obras images and IDs list sizes.")
                    }
                }
            }
        }

        Text(
            "Converse com Gemini:",
            modifier = Modifier.wrapContentSize(Alignment.Center),
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