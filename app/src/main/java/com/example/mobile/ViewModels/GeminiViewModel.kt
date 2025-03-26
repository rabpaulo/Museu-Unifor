package com.example.mobile.ViewModels

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeminiViewModel:ViewModel() {
    var prompt by mutableStateOf("")
    private val _resposta = MutableStateFlow("")
    val resposta: StateFlow<String> get() = _resposta

    fun callGemini(value: String) {
        prompt = value
        viewModelScope.launch {
            GeminiIA()
        }
    }

    @SuppressLint("SecretInSource")
    private suspend fun GeminiIA() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "" // Insira sua chave API aqui
            )
            val textoRetorno = generativeModel.generateContent("A pergunta a seguir, responda em 300 caracteres" + prompt).text.toString()
            _resposta.emit(textoRetorno)
        }
    }


