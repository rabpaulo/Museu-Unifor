package com.example.mobile.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.mobile.ui.components.AuthorSelectorField
import com.example.mobile.ui.components.BackButton
import com.example.mobile.ui.components.DatePickerField
import com.example.mobile.ui.components.ImagePickerField
import com.example.mobile.ui.viewmodel.AutorViewModel
import com.example.mobile.ui.viewmodel.ObraViewModel
import com.example.mobile.util.MuseumImage
import com.example.mobile.util.poppinsFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarObraScreen(
    navController: NavController,
    autorViewModel: AutorViewModel,
    obraViewModel: ObraViewModel
) {
    val context = LocalContext.current
    val autores by autorViewModel.autores.collectAsState()
    val isLoading by obraViewModel.isLoading.collectAsState()

    var autorId by remember { mutableStateOf("") }
    var autorNome by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    var autorError by remember { mutableStateOf(false) }
    var nomeError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var descricaoError by remember { mutableStateOf(false) }
    var imageError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Cadastrar Obra",
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
                    if (image.isNotEmpty()) {
                        MuseumImage(
                            imageSource = image,
                            contentDescription = "Preview da Obra",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    ImagePickerField(
                        labelName = "Selecionar Foto da Obra",
                        onImageSelected = {
                            image = it
                            imageError = false
                        }
                    )

                    if (imageError) {
                        Text("Selecione uma imagem para a obra", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(16.dp))

                    AuthorSelectorField(
                        selectedAuthorName = autorNome,
                        autores = autores,
                        onAuthorSelected = { selected ->
                            autorId = selected.id
                            autorNome = selected.nome
                            autorError = false
                        }
                    )

                    if (autorError) {
                        Text("Selecione o autor da obra", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nome,
                        onValueChange = {
                            nome = it
                            nomeError = false
                        },
                        label = { Text("Título da Obra", fontFamily = poppinsFamily) },
                        leadingIcon = {
                            Icon(Icons.Default.Palette, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        isError = nomeError,
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (nomeError) {
                        Text("O título não pode estar vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(16.dp))

                    DatePickerField(
                        selectedDate = data,
                        onDateSelected = {
                            data = it
                            dateError = false
                        }
                    )

                    if (dateError) {
                        Text("O ano de criação não pode estar vazio", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = {
                            descricao = it
                            descricaoError = false
                        },
                        label = { Text("Descrição e Técnica da Obra", fontFamily = poppinsFamily) },
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        isError = descricaoError,
                        minLines = 3,
                        maxLines = 6,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (descricaoError) {
                        Text("A descrição não pode estar vazia", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            autorError = autorId.isBlank() || autorNome.isBlank()
                            nomeError = nome.isBlank()
                            dateError = data.isBlank()
                            descricaoError = descricao.isBlank()
                            imageError = image.isBlank()

                            if (!autorError && !nomeError && !dateError && !descricaoError && !imageError) {
                                obraViewModel.addObra(
                                    autorId = autorId,
                                    autorNome = autorNome,
                                    nome = nome.trim(),
                                    data = data.trim(),
                                    descricao = descricao.trim(),
                                    image = image
                                ) { success, message ->
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    if (success) {
                                        navController.popBackStack()
                                    }
                                }
                            }
                        },
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Salvar Obra",
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
