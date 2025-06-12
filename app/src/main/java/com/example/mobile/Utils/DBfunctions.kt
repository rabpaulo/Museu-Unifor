package com.example.mobile.Utils

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.mobile.R

suspend fun getAutores(): List<String> {
    val db = FirebaseFirestore.getInstance()

    return try {
        val result = db.collection("autor").get().await()

        result.documents.mapNotNull { it.getString("nome") }
    } catch (e: Exception) {
        emptyList()
    }
}

suspend fun getAutorDocumentId(nome: String): String? {
    val db = FirebaseFirestore.getInstance()

    val query = db.collection("autor")
        .whereEqualTo("nome", nome)
        .get()
        .await()

    return query.documents.firstOrNull()?.id.toString()
}

@Composable
fun ListaDeObras(navController: NavController, mode: String) {
    var searchQuery by remember { mutableStateOf("") }
    var obras by remember { mutableStateOf(listOf<String>()) }
    var filteredObras by remember { mutableStateOf(listOf<String>()) }
    val obraIds = remember { mutableStateOf(listOf<String>()) }
    val authorIds = remember { mutableStateOf(listOf<String>()) }
    val obraImages = remember { mutableStateOf(listOf<String>()) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        // Fetching data from Firebase
        db.collection("autor")
            .get()
            .addOnSuccessListener { documents ->
                val allObras = mutableListOf<String>()
                val allObraIds = mutableListOf<String>()
                val allAuthorIds = mutableListOf<String>()
                val allObraImages = mutableListOf<String>()

                documents.forEach { author ->
                    val authorId = author.id
                    db.collection("autor")
                        .document(authorId)
                        .collection("obras")
                        .get()
                        .addOnSuccessListener { obraDocs ->
                            obraDocs.forEach { obra ->
                                allObras.add(obra.getString("nome").orEmpty())
                                allObraIds.add(obra.id)
                                allAuthorIds.add(authorId)
                                allObraImages.add(obra.getString("image").orEmpty())
                            }

                            // Update UI state after data is fetched
                            obras = allObras
                            filteredObras = allObras
                            obraIds.value = allObraIds
                            authorIds.value = allAuthorIds
                            obraImages.value = allObraImages
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching documents", e)
            }
    }

    // Search bar
    Column {
        Surface(
            color = Color.LightGray,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        filteredObras = if (query.isEmpty()) {
                            obras
                        } else {
                            obras.filter { it.contains(query, ignoreCase = true) }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search...",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }

        // Display filtered obra images in a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            items(filteredObras.size) { index ->
                val obraIndex = obras.indexOf(filteredObras[index])
                val base64String = obraImages.value.getOrNull(obraIndex).orEmpty()

                val bitmap = remember(base64String) {
                    if (base64String.isNotEmpty()) {
                        base64ToBitmap(base64String)  // Decode base64 image to bitmap
                    } else {
                        null
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 4.dp)
                        .clickable {
                            val obraId = obraIds.value.getOrNull(obraIndex)
                            val authorId = authorIds.value.getOrNull(obraIndex)
                            if (obraId != null) {
                                if (mode == "view") {
                                    navController.navigate("ObraGenerica/$authorId/$obraId")
                                } else if (mode == "edit") {
                                    navController.navigate("EditarObra/$authorId/$obraId")
                                }
                            }
                        }
                ) {
                    if (bitmap != null) {
                        Image(
                            painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = "Obra Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)  // Ensures the image is square
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop // Crop the image to fit the grid cell
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.pfp),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }

                    // Title below the image
                    Text(
                        text = filteredObras[index],
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Center,
                        maxLines = 1, // Limit to one line
                        overflow = TextOverflow.Ellipsis // Add ellipsis if the text is too long
                    )
                }
            }
        }
    }
}

@Composable
fun ListaDeAutores(navController: NavController, mode: String) {
    var searchQuery by remember { mutableStateOf("") }
    val autores = remember { mutableStateOf(listOf<String>()) }
    val descricao = remember { mutableStateOf(listOf<String>()) }
    val data = remember { mutableStateOf(listOf<String>()) }
    val authorIds = remember { mutableStateOf(listOf<String>()) }
    val authorImages = remember { mutableStateOf(listOf<String>()) }

    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        db.collection("autor")
            .get()
            .addOnSuccessListener { documents ->
                val allAutores = mutableListOf<String>()
                val allDescricao = mutableListOf<String>()
                val allData = mutableListOf<String>()
                val allAuthorIds = mutableListOf<String>()
                val allAuthorImages = mutableListOf<String>()

                documents.forEach { author ->
                    allAutores.add(author.getString("nome").orEmpty())
                    allDescricao.add(author.getString("descricao").orEmpty())
                    allData.add(author.getString("data").orEmpty())
                    allAuthorIds.add(author.id)
                    allAuthorImages.add(author.getString("image").orEmpty())
                }

                autores.value = allAutores
                descricao.value = allDescricao
                data.value = allData
                authorIds.value = allAuthorIds
                authorImages.value = allAuthorImages
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreError", "Error fetching documents", e)
            }
    }

    // Search bar integrated directly
    Column {
        Surface(
            color = Color.LightGray,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                    },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search...",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }

        // Filtered list based on search query
        val filteredAutores = autores.value.filter {
            it.contains(searchQuery, ignoreCase = true)
        }

        LazyColumn {
            items(filteredAutores.size) { index ->
                val authorIndex = autores.value.indexOf(filteredAutores[index])
                Row(modifier = Modifier.padding(8.dp)) {
                    val base64String = authorImages.value.getOrNull(authorIndex).orEmpty()
                    val bitmap = remember(base64String) {
                        base64ToBitmap(base64String)
                    }

                    // Display author image (use placeholder if no image)
                    if (bitmap != null) {
                        Image(
                            painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = "Author Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .clickable {
                                    val authorId = authorIds.value.getOrNull(authorIndex)
                                    if (authorId != null) {
                                        if (mode == "view") {
                                            navController.navigate("AutorGenerica/$authorId")
                                        } else if (mode == "edit") {
                                            navController.navigate("EditarAutor/$authorId")
                                        }
                                    }
                                },
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.pfp),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .clickable {
                                    val authorId = authorIds.value.getOrNull(authorIndex)
                                    if (authorId != null) {
                                        if (mode == "view") {
                                            navController.navigate("AutorGenerica/$authorId")
                                        } else if (mode == "edit") {
                                            navController.navigate("EditarAutor/$authorId")
                                        }
                                    }
                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    // Column for author name and description
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = filteredAutores[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                        if (authorIndex < descricao.value.size && authorIndex < data.value.size) {
                            Text(text = data.value[authorIndex], modifier = Modifier.padding(2.dp))
                            Text(text = descricao.value[authorIndex], maxLines = 2)
                        }
                    }
                }
                Spacer(Modifier.height(10.dp)) // Spacer between items
            }
        }
    }
}

@Composable
fun selecionarAutor(): String {

    var autores by remember { mutableStateOf<List<String>>(emptyList()) }
    var showPicker by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var autorSelecionado by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        autores = getAutores()
        if(!autores.isEmpty()){
            selectedOption = autores[0]
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Autor: $autorSelecionado",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { showPicker = true }) {
            Text("Selecionar Autor")
        }

        if (showPicker) {
            Dialog(onDismissRequest = { showPicker = false }) {
                Surface(shape = MaterialTheme.shapes.medium) {
                    LazyColumn {
                        itemsIndexed(autores) { index, option ->
                            Text(
                                text = option,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedOption = option
                                        autorSelecionado = option
                                        showPicker = false
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    return autorSelecionado
}