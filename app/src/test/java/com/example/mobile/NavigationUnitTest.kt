package com.example.mobile

import com.example.mobile.ui.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationUnitTest {

    @Test
    fun staticRoutes_haveExpectedStrings() {
        assertEquals("PrimeiraTela", Screen.PrimeiraTela.route)
        assertEquals("EscolhaVisitanteLogin", Screen.EscolhaVisitaLogin.route)
        assertEquals("Login", Screen.Login.route)
        assertEquals("PrincipalVisitante", Screen.PrincipalVisitante.route)
        assertEquals("DetalheExpo", Screen.DetalheExpo.route)
        assertEquals("ExposicaoAutores", Screen.ExposicaoAutores.route)
        assertEquals("ExposicaoObras", Screen.ExposicaoObras.route)
        assertEquals("AutoresADM", Screen.AutoresADM.route)
        assertEquals("ObrasADM", Screen.ObrasADM.route)
        assertEquals("AdicionarAutor", Screen.AdicionarAutor.route)
        assertEquals("AdicionarObra", Screen.AdicionarObra.route)
    }

    @Test
    fun parameterizedRoutes_createCorrectRoutePaths() {
        assertEquals("AutorGenerica/auth123", Screen.AutorGenerica.createRoute("auth123"))
        assertEquals("ObraGenerica/auth123/obra456", Screen.ObraGenerica.createRoute("auth123", "obra456"))
        assertEquals("EditarAutor/auth123", Screen.EditarAutor.createRoute("auth123"))
        assertEquals("EditarObra/auth123/obra456", Screen.EditarObra.createRoute("auth123", "obra456"))
    }
}
