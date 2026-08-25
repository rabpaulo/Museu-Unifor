package com.example.mobile

import com.example.mobile.data.model.Autor
import com.example.mobile.data.model.ChatMessage
import com.example.mobile.data.model.Obra
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelUnitTest {

    @Test
    fun autor_toMap_containsCorrectValues() {
        val autor = Autor(
            id = "author_123",
            nome = "Cândido Portinari",
            data = "1903 - 1962",
            descricao = "Pintor brasileiro modernista",
            image = "base64image"
        )

        val map = autor.toMap()

        assertEquals("Cândido Portinari", map["nome"])
        assertEquals("1903 - 1962", map["data"])
        assertEquals("Pintor brasileiro modernista", map["descricao"])
        assertEquals("base64image", map["image"])
    }

    @Test
    fun obra_toMap_containsCorrectValues() {
        val obra = Obra(
            id = "obra_456",
            autorId = "author_123",
            autor = "Cândido Portinari",
            nome = "Mestiço",
            data = "1934",
            descricao = "Pintura a óleo sobre tela",
            image = "base64obraimage"
        )

        val map = obra.toMap()

        assertEquals("Cândido Portinari", map["autor"])
        assertEquals("Mestiço", map["nome"])
        assertEquals("1934", map["data"])
        assertEquals("Pintura a óleo sobre tela", map["descricao"])
        assertEquals("base64obraimage", map["image"])
    }

    @Test
    fun chatMessage_createsCorrectState() {
        val userMsg = ChatMessage(text = "Quem é o autor?", isUser = true)
        val botMsg = ChatMessage(text = "O autor é Portinari.", isUser = false)

        assertTrue(userMsg.isUser)
        assertEquals("Quem é o autor?", userMsg.text)

        assertFalse(botMsg.isUser)
        assertEquals("O autor é Portinari.", botMsg.text)
    }
}
