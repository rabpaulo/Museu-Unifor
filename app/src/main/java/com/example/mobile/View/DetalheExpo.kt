package com.example.mobile.View

import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.QUEUE_FLUSH
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.View.utils.VLibras
import com.example.mobile.View.utils.dynamicSpacerHeight
import com.example.mobile.View.utils.poppinsFamily
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheExpo(navController: NavController){
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(dynamicSpacerHeight(0.03f), 0.dp)
            .wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState())
    ) {
        // Center Aligned TopAppBar with the title and back button
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Exposição Atual",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
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

        Box(Modifier.fillMaxWidth()){
            // Description text of the exhibition
            Text(
                "Centelhas Em Movimento",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical =  20.dp), // Adjust vertical padding for readability

                style = TextStyle(fontSize = 15.sp),
                textAlign = TextAlign.Center,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal


            )
        }

        Image(
            painter = painterResource(id = R.drawable.fotoprinci),
            contentDescription = "Description of the image",
            Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
//                .clip(RoundedCornerShape(40.dp))
        )

        Box (Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)){
            var descricao = stringResource(R.string.descricao)
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
                onClick = { textToSpeech?.speak(descricao, QUEUE_FLUSH, null, null) },
                icon = { Icon(Icons.Filled.PlayArrow, "Extended floating action button.") },
                text = { Text(text = "Ler Texto") },
            )



            Spacer(Modifier.width(16.dp))

        }

        Spacer(Modifier.height(dynamicSpacerHeight(0.02f)))

        VLibras(stringResource(R.string.descricao), isSystemInDarkTheme())

       Spacer(Modifier.height(60.dp))
    }
}