package com.example.mobile.Controller

sealed class Screen (val route:String) {
    object PrimeiraTela: Screen("PrimeiraTela")
    object EscolhaVisitaLogin: Screen("EscolhaVisitanteLogin")
    object Login: Screen("Login")

    object PrincipalVisitante: Screen("PrincipalVisitante")
    object DetalheExpo: Screen("DetalheExpo")

    object AutoresADM: Screen("AutoresADM")
    object ObrasADM: Screen("ObrasADM")


    object ObrasDeUmAutor: Screen("ObrasDeUmAutor")

    // Tela de Adicionar autor/obra
    object AdicionarAutor: Screen("AdicionarAutor")
    object AdicionarObra: Screen("AdicionarObra")

    // Tela de Edição de autor/obra
    object EditarAutor: Screen("EditarAutor")
    object EditarObra: Screen("EditarObra")

    // Telas de navegacao
    object ExposicaoAutores: Screen("ExposicaoAutores")
    object ExposicaoObras: Screen("ExposicaoObras")

    // Telas temporarais
    object GenericaAutor: Screen("GenericaAutor")
    object GenericaObras: Screen("GenericaObras")
}