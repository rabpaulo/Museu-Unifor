package com.example.mobile.ui.navigation

sealed class Screen(val route: String) {
    object PrimeiraTela : Screen("PrimeiraTela")
    object EscolhaVisitaLogin : Screen("EscolhaVisitanteLogin")
    object Login : Screen("Login")
    object PrincipalVisitante : Screen("PrincipalVisitante")
    object DetalheExpo : Screen("DetalheExpo")
    object ExposicaoAutores : Screen("ExposicaoAutores")
    object ExposicaoObras : Screen("ExposicaoObras")
    object AutoresADM : Screen("AutoresADM")
    object ObrasADM : Screen("ObrasADM")
    object AdicionarAutor : Screen("AdicionarAutor")
    object AdicionarObra : Screen("AdicionarObra")

    object AutorGenerica : Screen("AutorGenerica/{id}") {
        fun createRoute(id: String) = "AutorGenerica/$id"
    }

    object ObraGenerica : Screen("ObraGenerica/{idAutor}/{idObra}") {
        fun createRoute(idAutor: String, idObra: String) = "ObraGenerica/$idAutor/$idObra"
    }

    object EditarAutor : Screen("EditarAutor/{id}") {
        fun createRoute(id: String) = "EditarAutor/$id"
    }

    object EditarObra : Screen("EditarObra/{idAutor}/{idObra}") {
        fun createRoute(idAutor: String, idObra: String) = "EditarObra/$idAutor/$idObra"
    }
}
