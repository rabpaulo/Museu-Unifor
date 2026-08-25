package com.example.mobile.data.repository

import com.google.ai.client.generativeai.GenerativeModel

class GeminiRepository(
    private val apiKey: String = "AIzaSyA91dqFrrPGem4_UaQ5hMv66YaLIEyxH3w",
    private val modelName: String = "gemini-1.5-flash"
) {
    suspend fun generateResponse(prompt: String): Result<String> {
        return try {
            val generativeModel = GenerativeModel(
                modelName = modelName,
                apiKey = apiKey
            )
            val fullPrompt = "Você é o assistente virtual do Museu Unifor para a exposição 'Centelhas em Movimento'. Responda de forma concisa e cultural em até 300 caracteres: $prompt"
            val response = generativeModel.generateContent(fullPrompt)
            val text = response.text.orEmpty().ifBlank { "Sem resposta disponível." }
            Result.success(text)
        } catch (e: Exception) {
            // Intelligent fallback responses for art inquiries
            val fallback = when {
                prompt.contains("portinari", ignoreCase = true) || prompt.contains("retirantes", ignoreCase = true) ->
                    "Cândido Portinari (1903-1962) retratou a alma e as lutas sociais do povo brasileiro com grande lirismo e impacto visual."
                prompt.contains("tarsila", ignoreCase = true) || prompt.contains("abaporu", ignoreCase = true) ->
                    "Tarsila do Amaral foi a grande pioneira da Antropofagia, unindo vanguardas europeias às raízes e cores brasileiras."
                prompt.contains("unifor", ignoreCase = true) || prompt.contains("exposi", ignoreCase = true) ->
                    "A mostra 'Centelhas em Movimento' reúne preciosidades da Coleção Igor Queiroz Barroso no Espaço Cultural Unifor."
                else ->
                    "A arte brasileira moderna reflete a diversidade, cores e expressividade de nossa rica história cultural no Espaço Cultural Unifor."
            }
            Result.success(fallback)
        }
    }
}
