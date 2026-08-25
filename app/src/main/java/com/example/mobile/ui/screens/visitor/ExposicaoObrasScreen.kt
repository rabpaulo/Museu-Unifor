package com.example.mobile.ui.screens.visitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.ui.components.BackButton
import com.example.mobile.ui.components.ListaDeObras
import com.example.mobile.ui.components.SegmentedTabSelector
import com.example.mobile.ui.navigation.Screen
import com.example.mobile.ui.viewmodel.ObraViewModel
import com.example.mobile.util.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposicaoObrasScreen(
    navController: NavController,
    obraViewModel: ObraViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Conheça as Obras",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            navigationIcon = {
                BackButton(navController = navController, modifier = Modifier.padding(start = 8.dp))
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
        )

        SegmentedTabSelector(
            selectedTab = "Obras",
            onTabSelected = { tab ->
                if (tab == "Autores") {
                    navController.navigate(Screen.ExposicaoAutores.route) {
                        popUpTo(Screen.ExposicaoObras.route) { inclusive = true }
                    }
                }
            }
        )

        ListaDeObras(
            navController = navController,
            mode = "view",
            obraViewModel = obraViewModel
        )
    }
}
