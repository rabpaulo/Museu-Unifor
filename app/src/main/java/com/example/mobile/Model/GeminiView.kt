package com.example.mobile.Model

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeminiView:ViewModel() {
    var prompt by mutableStateOf("")
    internal val _resposta = MutableStateFlow("")
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
            apiKey = "AIzaSyA91dqFrrPGem4_UaQ5hMv66YaLIEyxH3w" // Api Key
            )
            val textoRetorno = generativeModel.generateContent("A pergunta a seguir, responda em 300 caracteres" + prompt).text.toString()
            _resposta.emit(textoRetorno)
        }
    }


