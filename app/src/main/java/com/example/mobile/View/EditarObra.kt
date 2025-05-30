package com.example.mobile.View

import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobile.R
import com.example.mobile.Models.ObraViewModel
import com.example.mobile.Controller.Screen
import com.example.mobile.View.utils.BackButton
import com.example.mobile.View.utils.SelectableImage
import com.example.mobile.View.utils.base64ToBitmap
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditarObra(navController: NavController, idAutor: String?, idObra: String?) {
    val viewModel: ObraViewModel = viewModel()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var decodedBitmap by remember { mutableStateOf<Bitmap?>(null) } // For storing the decoded image

    // Load obra data only once when the Composable is first launched
    LaunchedEffect(db) {
        idAutor?.let { autorId ->
            idObra?.let { obraId ->
                db.collection("autor")
                    .document(autorId)
                    .collection("obras")
                    .document(obraId)
                    .get()
                    .addOnSuccessListener { document ->
                        // Set ViewModel values
                        viewModel.autor = document.getString("autor").orEmpty()
                        viewModel.nome = document.getString("nome").orEmpty()
                        viewModel.data = document.getString("data").orEmpty()
                        viewModel.descricao = document.getString("descricao").orEmpty()
                        viewModel.image = document.getString("image").orEmpty()

                        // Decode Base64 string into Bitmap
                        val base64String = viewModel.image
                        if (base64String.isNotEmpty()) {
                            decodedBitmap = base64ToBitmap(base64String)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirestoreError", "Error fetching obra data", e)
                    }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(navController)
        }

        Text(
            text = "Editar Obra",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // Dynamic image display
        if (decodedBitmap != null) {
            Image(
                painter = BitmapPainter(decodedBitmap!!.asImageBitmap()),
                contentDescription = "Obra Image",
                Modifier.size(150.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.pfp), // Placeholder
                contentDescription = "Placeholder Image",
                Modifier.size(150.dp)
            )
        }

        SelectableImage(
            labelName = "Mudar foto",
            context = context,
            onImageSelected = { base64String ->
               viewModel.image = base64String
            }
        )

        Spacer(Modifier.height(10.dp))

        TextField(
            value = viewModel.nome,
            onValueChange = { viewModel.nome = it },
            label = { Text(text = "Editar nome") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.height(10.dp))

        TextField(
            value = viewModel.data,
            onValueChange = { viewModel.data = it },
            label = { Text(text = "Editar data") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.height(10.dp))

        TextField(
            value = viewModel.descricao,
            onValueChange = { viewModel.descricao = it },
            label = { Text(text = "Editar descrição") },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(Modifier.height(10.dp))

        // Buttons for delete and edit
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Delete
            Button(
                onClick = {
                    viewModel.DeletarObra(context, idAutor + "", idObra + "")
                    navController.navigate(Screen.ObrasADM.route)
                },
                Modifier.padding(20.dp)
            ) {
                Text("Deletar Obra")
            }

            // Edit
            Button(
                onClick = {
                    viewModel.EditarObra(context, idAutor + "", idObra + "")
                    navController.navigate(Screen.ObrasADM.route)
                },
                Modifier.padding(20.dp)
            ) {
                Text("Editar Obra")
            }
        }

        Button(
            onClick = { navController.navigate("EditarAutor/$idAutor") }
        ) {
            Text("Autor")
        }

        Spacer(Modifier.height(40.dp))
    }
}
