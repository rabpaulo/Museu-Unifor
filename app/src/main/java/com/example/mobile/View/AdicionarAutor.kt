package com.example.mobile.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobile.Factory.ViewModelFactory
import com.example.mobile.Models.AutorViewModel
import com.example.mobile.View.utils.BackButton
import com.example.mobile.View.utils.ImagePicker
import com.example.mobile.View.utils.datePicker

@Composable
fun AdicionarAutor(navController: NavController){
    var viewModel = ViewModelFactory().CreateViewModel("Autor") as AutorViewModel
    val context = LocalContext.current

    // Variáveis de estado locais para campos editáveis
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    // Variáveis para prevenir campos vazios
    var nomeError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var descricaoError by remember { mutableStateOf(false) }
    var imageError by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // BackButton nao funciona se tiver uma Column scrollavel: Linha 50
        // Tive q adicionar uma Row com o BackButton dentro
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            BackButton(navController)
        }

        Text(
            text = "Adicionar autor",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        viewModel.image = ImagePicker(context, "Selecionar Imagem")

        if(imageError) Text("imagem não pode estar vazia", color = Color.Red)

        Spacer(Modifier.height(20.dp))

        // Definir nome
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        if (nomeError) Text("Nome não pode estar vazio", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        //Definir descricao
        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Biografia") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        if (descricaoError) Text("Biografia não pode estar vazia", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        // Definir data
        viewModel.date = datePicker(context)

        if (dateError) Text("Data não pode estar vazia", color = Color.Red)

        Button(
            onClick = {
                // Checagem se os campos estao vazios
                nomeError = nome.isEmpty()
                dateError = viewModel.date.isEmpty()
                descricaoError = descricao.isEmpty()
                imageError = viewModel.image.isEmpty()

                if (!nomeError && !dateError && !descricaoError && !imageError) {
                    // Atualiza o viewModel com os valores dos campos
                    viewModel.nome = nome
                    viewModel.descricao = descricao
                    viewModel.cadastrarAutor(context)
                    navController.popBackStack()
                }
            },
            Modifier.padding(20.dp)
        ) {
            Text("Confirmar")
        }
    }
}
