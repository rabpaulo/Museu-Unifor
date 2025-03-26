package com.example.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ViewModels.GeminiViewModel
import com.example.mobile.screens.*
import com.example.mobile.screens.utils.ListaDeAutores

@Composable
fun Navigation(){
    val navController = rememberNavController();
    val gemini = GeminiViewModel()

    NavHost(navController = navController, startDestination = Screen.PrimeiraTela.route){
        composable(route = Screen.PrimeiraTela.route){
            PrimeiraTela(navController)
        }
        composable(route = Screen.EscolhaVisitaLogin.route){
            EscolhaVisitaLogin(navController)
        }
        composable(route = Screen.Login.route){
            Login(navController)
        }
        composable(route = Screen.PrincipalVisitante.route){
            PrincipalVisitante(navController)
        }

        // Tres telas das 3 abas Autores/Obras/Exposicoes Anterioes
        composable(route = Screen.ExposicaoAutores.route){
            ExposicaoAutores(navController)
        }
        composable(route = Screen.ExposicaoObras.route){
            ExposicaoObras(navController)
        }

        // Tela falando sobre a exposicao atual
        composable(route = Screen.DetalheExpo.route){
            DetalheExpo(navController)
        }

        composable(route = "AutorGenerica/{id}") {
            backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AutorGenerica(navController, id = id, gemini)
        }

        composable(route = "ObraGenerica/{idAutor}/{idObra}"){
            backStackEntry ->
            val idAutor = backStackEntry.arguments?.getString("idAutor")
            val idObra = backStackEntry.arguments?.getString("idObra")
            ObrasGenerica(navController, idAutor = idAutor, idObra = idObra, gemini)
        }

        // Telas do ADM
        composable(route = Screen.AutoresADM.route){
            AutoresADM(navController)
        }
        composable(route = Screen.ObrasADM.route){
            ObrasADM(navController)
        }

        // Tela Editar Autor/Obra
        composable(route = "EditarAutor/{id}"){
                backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            EditarAutor(navController, id.toString())
        }

        composable(route = "EditarObra/{idAutor}/{idObra}"){
                backStackEntry ->
            val idAutor = backStackEntry.arguments?.getString("idAutor")
            val idObra = backStackEntry.arguments?.getString("idObra")
            EditarObra(navController, idAutor = idAutor, idObra = idObra)
        }

        // Tela Adicionar Autor/Obra
        composable(route = Screen.AdicionarAutor.route){
            AdicionarAutor(navController)
        }
        composable(route = Screen.AdicionarObra.route){
            AdicionarObra(navController)
        }
    }
}
















