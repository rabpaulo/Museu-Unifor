package com.example.mobile.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobile.Controller.Screen
import com.example.mobile.View.utils.dynamicSpacerHeight
import com.example.mobile.View.utils.poppinsFamily
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    CenterAlignedTopAppBar(
        title = {},
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

    Box(Modifier.fillMaxWidth()){
        // Description text of the exhibition
        Text(
            "Bem-Vindo!",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dynamicSpacerHeight(0.01f), dynamicSpacerHeight(0.23f)),
            style = TextStyle(fontSize = 20.sp),
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold


        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Email")},
        )
        Spacer(modifier = Modifier.height(dynamicSpacerHeight(0.08f)))
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(dynamicSpacerHeight(0.06f)))

        Button(
            onClick = { checkCredentials(login, senha, navController, onError = { errorMessage = it }) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Entrar")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

fun checkCredentials(
    login: String,
    senha: String,
    navController: NavController,
    onError: (String) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.signInWithEmailAndPassword(login, senha)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(Screen.AutoresADM.route)
            } else {
                onError("Erro ao acessar o sistema. Tente novamente.")
            }
        }
        .addOnFailureListener {
            onError("Login ou senha inválidos")
        }
}

//fun checkCredentials(
//    login: String,
//    senha: String,
//    navController: NavController,
//    onError: (String) -> Unit
//) {
//    val db = FirebaseFirestore.getInstance()
//    FirebaseAuthCredentialsProvider.
//
//    db.collection("credenciais")
//        .whereEqualTo("login", login)
//        .whereEqualTo("senha", senha)
//        .get()
//        .addOnSuccessListener {
//            documents ->
//            if (!documents.isEmpty) {
//                navController.navigate(Screen.AutoresADM.route)
//            } else {
//                onError("Login ou senha inválidos")
//            }
//        }
//        .addOnFailureListener {
//            exception ->
//            onError("Erro ao acessar o sistema. Tente novamente.")
//        }
//}

