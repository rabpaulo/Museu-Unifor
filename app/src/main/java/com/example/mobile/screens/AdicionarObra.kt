package com.example.mobile.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobile.R
import com.example.mobile.ViewModels.ObraViewModel
import com.example.mobile.screens.utils.BackButton
import com.example.mobile.screens.utils.ImagePicker
import com.example.mobile.screens.utils.datePicker
import com.example.mobile.screens.utils.getAutorDocumentId
import com.example.mobile.screens.utils.selecionarAutor


@Composable
fun AdicionarObra(navController: NavController){
    val viewModel: ObraViewModel = viewModel()
    val context = LocalContext.current

    var autorId by remember { mutableStateOf("") }

    // Variaves previnir campos vazios
    var autorError by remember { mutableStateOf(false) }
    var nomeError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }
    var descricaoError by remember { mutableStateOf(false) }
    var imageError by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // BackButton nao funciona se tiver uma Column scrollavel: Linha 57
        // Tive q adicionar uma Row com o BackButton dentro
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            BackButton(navController)
        }

        Text(
            text = "Adicionar Obra",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(40.dp))

        viewModel.image = ImagePicker(context, "Selecionar Imagem")

        if(imageError) Text("Imagem não pode estar vazia", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        // Definir autor
        var autor = selecionarAutor()
        viewModel.autor = autor
        if (autorError) Text("Autor não pode estar vazio", color = Color.Red)

        LaunchedEffect(autor) {
            autorId = getAutorDocumentId(autor).toString()
        }

        Spacer(Modifier.height(10.dp))

        // Definir data
        viewModel.data = datePicker(context)
        if (dateError) Text("Data não pode estar vazia", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        // Definir nome
        TextField(
            value = viewModel.nome,
            onValueChange = { viewModel.nome = it},
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (nomeError) Text("Nome não pode estar vazio", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        // Definir descricao
        TextField(
            value = viewModel.descricao,
            onValueChange = { viewModel.descricao = it},
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        if (descricaoError) Text("Descrição não pode ser vazia", color = Color.Red)

        Spacer(Modifier.height(10.dp))

        Button(
            onClick = {
            // Checagem se os campos estao vazios
            autorError = viewModel.autor.isEmpty()
            nomeError = viewModel.nome.isEmpty()
            dateError = viewModel.data.isEmpty()
            descricaoError = viewModel.descricao.isEmpty()
            imageError = viewModel.image.isEmpty()

                if (!autorError && !nomeError && !dateError && !descricaoError && !imageError) {
                    viewModel.adicionarObra(context, autorId)
                    navController.popBackStack()
                }
            }
            , Modifier.padding(20.dp)) {
            Text("Confirmar")
        }
    }
}


