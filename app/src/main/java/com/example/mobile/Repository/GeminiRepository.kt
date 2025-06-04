package com.example.mobile.Repository

import android.annotation.SuppressLint
import com.example.mobile.Model.GeminiView
import com.google.ai.client.generativeai.GenerativeModel

class GeminiRepository(private val gemini: GeminiView) {
    suspend fun callGemini(value: String) {
        gemini.prompt = value
        GeminiIA()
    }

    @SuppressLint("SecretInSource")
    private suspend fun GeminiIA() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "AIzaSyA91dqFrrPGem4_UaQ5hMv66YaLIEyxH3w" // Api Key
        )
        val textoRetorno = generativeModel.generateContent("A pergunta a seguir, responda em 300 caracteres" + gemini.prompt).text.toString()
        gemini._resposta.emit(textoRetorno)
    }
}