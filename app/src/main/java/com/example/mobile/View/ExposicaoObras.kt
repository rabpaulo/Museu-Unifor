package com.example.mobile.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.Controller.Screen
import com.example.mobile.Utils.ListaDeObras
import com.example.mobile.Utils.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposicaoObras(navController: NavController){


    Column(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
            .padding(10.dp)) {

        // Center Aligned TopAppBar with the title and back button
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Conheça as Obras",
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
        Row(
            Modifier
                .padding(top = 10.dp)
                .wrapContentSize(Alignment.TopCenter)){
            TextButton(onClick = {navController.navigate(Screen.ExposicaoAutores.route)},
                modifier = Modifier.weight(1f)
                    .clip(RoundedCornerShape(0.dp)) // Optional: Ensure sharp corners for a perfect square,

                ){
                Text("Autores",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                    )
            }

            Spacer(Modifier.width(5.dp))
            TextButton(onClick = {},
                modifier = Modifier.weight(1f)
                ) {
                Text("Obras",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                )
            }

            Spacer(Modifier.width(5.dp))
            Spacer(Modifier.height(60.dp))
        }

        ListaDeObras(navController, "view")
    }
}