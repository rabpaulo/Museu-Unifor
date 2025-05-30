package com.example.mobile.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.Controller.Screen
import com.example.mobile.View.utils.BackButton
import com.example.mobile.View.utils.poppinsFamily

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mobile.View.utils.dynamicSpacerHeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalVisitante(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Center Aligned TopAppBar with the title and back button
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Bem-Vindo",
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

        // Content Box with padding for main content

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically) // This ensures the content is centered vertically
            ) {
                // Title Text for the exhibition name
                Text(
                    stringResource(R.string.expo_nome),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), // Adjust vertical padding for better spacing
                    textAlign = TextAlign.Center // Centered title
                )

                // Exhibition image with rounded corners
                Image(
                    painter = painterResource(id = R.drawable.alta), // Replace with actual image resource
                    contentDescription = "Description of the image",
                    modifier = Modifier
                        .size(250.dp) // Adjusted image size
                        .fillMaxWidth(0.8f) // Image width as a fraction of screen width
                        .align(Alignment.CenterHorizontally) // Center horizontally
                        .clip(RoundedCornerShape(16.dp)) // Rounded corners for a more elegant look
                        .padding(bottom = 16.dp) // Padding below the image
                )

                Box(Modifier.fillMaxWidth()){
                    // Description text of the exhibition
                    Text(
                        stringResource(R.string.descricao_expo_atual),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp, vertical =  30.dp), // Adjust vertical padding for readability

                        style = TextStyle(fontSize = 10.sp),
                        textAlign = TextAlign.Center,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal


                    )
                }

//                ImageCarousel(navController, autorId = "", images = "", ids = obraIds)

                // Button for "Conhecer autores e obras"
                Button(
                    onClick = { navController.navigate(Screen.ExposicaoAutores.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp) // Padding between buttons
                ) {
                    Text("Conhecer Autores e Obras")
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Button for "Detalhes Exposição Atual"
                Button(
                    onClick = { navController.navigate(Screen.DetalheExpo.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp) // Padding between buttons
                ) {
                    Text("Detalhes Exposicão Atual")
                }
            }
        }
    }




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Legacy2PrincipalVisitante(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {

        CenterAlignedTopAppBar(
            title = { Text("Bem-Vindo",
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp) },
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

        // Content Box with padding for main content
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Image and Text Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {

                Text(
                    stringResource(R.string.expo_nome),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = dynamicSpacerHeight(0.1f),
                        vertical = dynamicSpacerHeight(0.005f)
                    ))

                Image(
                    painter = painterResource(id = R.drawable.alta), // Replace with actual image resource
                    contentDescription = "Description of the image",
                    Modifier
                        .size(300.dp)
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(20.dp))
                )

                Text(
                    stringResource(R.string.descricao_expo_atual),
                    Modifier
                        .padding(horizontal = dynamicSpacerHeight(0.01f)
                            , vertical = dynamicSpacerHeight(0.01f))
                        .fillMaxWidth(0.8f),
                    style = TextStyle(fontSize = 11.sp),
                    textAlign = TextAlign.Center // Justify the text
                )

                Button(
                    onClick = { navController.navigate(Screen.ExposicaoAutores.route) },
                    Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Conhecer autores e obras")
                }

                Spacer(Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate(Screen.DetalheExpo.route) },
                    Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Detalhes Exposicão Atual")
                }
            }
        }
    }
}




@Composable
fun LegacyPrincipalVisitante(navController: NavController){

    BackButton(navController)

    Box(
        Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.TopCenter
    ){

        Text("Bem-Vindo!",
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp)
    }

    // Conteudo da pagina
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        Image(
            painter = painterResource(id = R.drawable.alta),
            contentDescription = "Description of the image",
            Modifier
                .size(300.dp)
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
        )

        Text("“Por acreditarmos que obras de arte podem estar em brasa quando reavivadas pelos olhares" +
                " dos públicos, concebemos a fricção entre elas como ação que desprende centelhas pelos ares”.",
            Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f),
            style = TextStyle(
                fontSize = 11.sp)
        )

        Button(onClick = {navController.navigate(Screen.ExposicaoAutores.route)}, Modifier.fillMaxWidth(0.8f)) {
            Text("Conhecer autores e obras")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = {navController.navigate(Screen.DetalheExpo.route)}, Modifier.fillMaxWidth(0.8f)) {
            Text("Detalhes Exposicão Atual")
        }
    }
}