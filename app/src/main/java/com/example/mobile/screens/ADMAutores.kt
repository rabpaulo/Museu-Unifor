package com.example.mobile.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.navigation.Screen
import com.example.mobile.screens.utils.BackButton
import com.example.mobile.screens.utils.ListaDeAutores
import com.example.mobile.screens.utils.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoresADM(navController: NavController){


    Column(Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
            .padding(10.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Gerenciamento",
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

        Row(Modifier.wrapContentSize(Alignment.TopCenter)){
            TextButton(onClick = {},
                Modifier.weight(1f)
            ){
                Text(
                    "Autores",
                    style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                )
            }

            Spacer(Modifier.width(5.dp))

            TextButton(onClick = {navController.navigate(Screen.ObrasADM.route)},
                Modifier.weight(1f)) {
                Text("Obras",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                    )
            }

            Spacer(Modifier.height(60.dp))
        }
        Spacer(Modifier.width(5.dp))
        Column {
            ListaDeAutores(navController, "edit")
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomEnd)
            .padding(vertical = 80.dp, horizontal = 30.dp)
    ){
        ExtendedFloatingActionButton(
            onClick = { navController.navigate(Screen.AdicionarAutor.route)},
            icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
            text = { Text(text = "Adicionar Autor") },
        )
    }
}