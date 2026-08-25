package com.example.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ui.screens.admin.AdicionarAutorScreen
import com.example.mobile.ui.screens.admin.AdicionarObraScreen
import com.example.mobile.ui.screens.admin.AutoresADMScreen
import com.example.mobile.ui.screens.admin.EditarAutorScreen
import com.example.mobile.ui.screens.admin.EditarObraScreen
import com.example.mobile.ui.screens.admin.ObrasADMScreen
import com.example.mobile.ui.screens.auth.LoginScreen
import com.example.mobile.ui.screens.visitor.AutorGenericaScreen
import com.example.mobile.ui.screens.visitor.DetalheExpoScreen
import com.example.mobile.ui.screens.visitor.EscolhaVisitaLoginScreen
import com.example.mobile.ui.screens.visitor.ExposicaoAutoresScreen
import com.example.mobile.ui.screens.visitor.ExposicaoObrasScreen
import com.example.mobile.ui.screens.visitor.ObraGenericaScreen
import com.example.mobile.ui.screens.visitor.PrimeiraTelaScreen
import com.example.mobile.ui.screens.visitor.PrincipalVisitanteScreen
import com.example.mobile.ui.viewmodel.AuthViewModel
import com.example.mobile.ui.viewmodel.AutorViewModel
import com.example.mobile.ui.viewmodel.GeminiViewModel
import com.example.mobile.ui.viewmodel.ObraViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    autorViewModel: AutorViewModel = viewModel(),
    obraViewModel: ObraViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    geminiViewModel: GeminiViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PrimeiraTela.route
    ) {
        composable(route = Screen.PrimeiraTela.route) {
            PrimeiraTelaScreen(navController = navController)
        }

        composable(route = Screen.EscolhaVisitaLogin.route) {
            EscolhaVisitaLoginScreen(navController = navController)
        }

        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(route = Screen.PrincipalVisitante.route) {
            PrincipalVisitanteScreen(navController = navController)
        }

        composable(route = Screen.ExposicaoAutores.route) {
            ExposicaoAutoresScreen(navController = navController, autorViewModel = autorViewModel)
        }

        composable(route = Screen.ExposicaoObras.route) {
            ExposicaoObrasScreen(navController = navController, obraViewModel = obraViewModel)
        }

        composable(route = Screen.DetalheExpo.route) {
            DetalheExpoScreen(navController = navController)
        }

        composable(route = Screen.AutorGenerica.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AutorGenericaScreen(
                navController = navController,
                autorId = id,
                autorViewModel = autorViewModel,
                geminiViewModel = geminiViewModel
            )
        }

        composable(route = Screen.ObraGenerica.route) { backStackEntry ->
            val idAutor = backStackEntry.arguments?.getString("idAutor")
            val idObra = backStackEntry.arguments?.getString("idObra")
            ObraGenericaScreen(
                navController = navController,
                idAutor = idAutor,
                idObra = idObra,
                obraViewModel = obraViewModel,
                geminiViewModel = geminiViewModel
            )
        }

        composable(route = Screen.AutoresADM.route) {
            AutoresADMScreen(navController = navController, autorViewModel = autorViewModel)
        }

        composable(route = Screen.ObrasADM.route) {
            ObrasADMScreen(navController = navController, obraViewModel = obraViewModel)
        }

        composable(route = Screen.AdicionarAutor.route) {
            AdicionarAutorScreen(navController = navController, autorViewModel = autorViewModel)
        }

        composable(route = Screen.AdicionarObra.route) {
            AdicionarObraScreen(
                navController = navController,
                autorViewModel = autorViewModel,
                obraViewModel = obraViewModel
            )
        }

        composable(route = Screen.EditarAutor.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            EditarAutorScreen(
                navController = navController,
                autorId = id,
                autorViewModel = autorViewModel
            )
        }

        composable(route = Screen.EditarObra.route) { backStackEntry ->
            val idAutor = backStackEntry.arguments?.getString("idAutor")
            val idObra = backStackEntry.arguments?.getString("idObra")
            EditarObraScreen(
                navController = navController,
                idAutor = idAutor,
                idObra = idObra,
                obraViewModel = obraViewModel
            )
        }
    }
}
