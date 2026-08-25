package com.example.mobile.ui.screens.admin

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.ui.components.BackButton
import com.example.mobile.ui.components.DatePickerField
import com.example.mobile.ui.components.ImagePickerField
import com.example.mobile.ui.navigation.Screen
import com.example.mobile.ui.viewmodel.ObraViewModel
import com.example.mobile.util.MuseumImage
import com.example.mobile.util.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarObraScreen(
    navController: NavController,
    idAutor: String?,
    idObra: String?,
    obraViewModel: ObraViewModel
) {
    val context = LocalContext.current
    val selectedObra by obraViewModel.selectedObra.collectAsState()
    val isLoading by obraViewModel.isLoading.collectAsState()

    var autorNome by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var hasInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(idAutor, idObra) {
        if (!idAutor.isNullOrBlank() && !idObra.isNullOrBlank()) {
            obraViewModel.loadObraDetails(idAutor, idObra)
        }
    }

    LaunchedEffect(selectedObra) {
        val obra = selectedObra
        if (obra != null && !hasInitialized) {
            autorNome = obra.autor
            nome = obra.nome
            data = obra.data
            descricao = obra.descricao
            image = obra.image
            hasInitialized = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Editar Obra",
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

        if (isLoading && !hasInitialized) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MuseumImage(
                            imageSource = image,
                            contentDescription = "Foto da Obra",
                            modifier = Modifier
                                .size(130.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(Modifier.height(12.dp))

                        ImagePickerField(
                            labelName = "Mudar Foto da Obra",
                            onImageSelected = { image = it }
                        )

                        Spacer(Modifier.height(16.dp))

                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            label = { Text("Título da Obra", fontFamily = poppinsFamily) },
                            leadingIcon = {
                                Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        DatePickerField(
                            selectedDate = data,
                            onDateSelected = { data = it }
                        )

                        Spacer(Modifier.height(16.dp))

                        OutlinedTextField(
                            value = descricao,
                            onValueChange = { descricao = it },
                            label = { Text("Descrição e Técnica", fontFamily = poppinsFamily) },
                            leadingIcon = {
                                Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            minLines = 3,
                            maxLines = 6,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))

                        if (!idAutor.isNullOrEmpty()) {
                            OutlinedButton(
                                onClick = { navController.navigate(Screen.EditarAutor.createRoute(idAutor)) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Editar Cadastro do Autor ($autorNome)",
                                    fontFamily = poppinsFamily,
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (!idAutor.isNullOrEmpty() && !idObra.isNullOrEmpty()) {
                                        obraViewModel.deleteObra(idAutor, idObra) { success, message ->
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            if (success) {
                                                navController.navigate(Screen.ObrasADM.route) {
                                                    popUpTo(Screen.ObrasADM.route) { inclusive = true }
                                                }
                                            }
                                        }
                                    }
                                },
                                enabled = !isLoading,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Excluir", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    if (!idAutor.isNullOrEmpty() && !idObra.isNullOrEmpty()) {
                                        obraViewModel.updateObra(
                                            autorId = idAutor,
                                            obraId = idObra,
                                            autorNome = autorNome,
                                            nome = nome.trim(),
                                            data = data.trim(),
                                            descricao = descricao.trim(),
                                            image = image
                                        ) { success, message ->
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            if (success) {
                                                navController.navigate(Screen.ObrasADM.route) {
                                                    popUpTo(Screen.ObrasADM.route) { inclusive = true }
                                                }
                                            }
                                        }
                                    }
                                },
                                enabled = !isLoading,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(50.dp)
                            ) {
                                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Salvar", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
