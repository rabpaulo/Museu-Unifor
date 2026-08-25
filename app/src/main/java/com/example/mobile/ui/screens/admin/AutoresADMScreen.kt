package com.example.mobile.ui.screens.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.ui.components.BackButton
import com.example.mobile.ui.components.ListaDeAutores
import com.example.mobile.ui.components.SegmentedTabSelector
import com.example.mobile.ui.navigation.Screen
import com.example.mobile.ui.viewmodel.AutorViewModel
import com.example.mobile.util.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoresADMScreen(
    navController: NavController,
    autorViewModel: AutorViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Painel Curatorial",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Gerenciamento de Autores",
                            fontFamily = poppinsFamily,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    BackButton(navController = navController, modifier = Modifier.padding(start = 8.dp))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            SegmentedTabSelector(
                selectedTab = "Autores",
                onTabSelected = { tab ->
                    if (tab == "Obras") {
                        navController.navigate(Screen.ObrasADM.route) {
                            popUpTo(Screen.AutoresADM.route) { inclusive = true }
                        }
                    }
                }
            )

            ListaDeAutores(
                navController = navController,
                mode = "edit",
                autorViewModel = autorViewModel
            )
        }

        ExtendedFloatingActionButton(
            onClick = { navController.navigate(Screen.AdicionarAutor.route) },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Adicionar Autor") },
            text = { Text(text = "Novo Autor", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        )
    }
}
