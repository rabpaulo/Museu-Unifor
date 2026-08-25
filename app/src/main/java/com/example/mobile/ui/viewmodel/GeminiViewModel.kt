package com.example.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.model.ChatMessage
import com.example.mobile.data.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel(
    private val geminiRepository: GeminiRepository = GeminiRepository()
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Olá! Me pergunte alguma curiosidade!", isUser = false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(text: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank() || _isLoading.value) return

        val userMessage = ChatMessage(text = trimmed, isUser = true)
        _messages.value = _messages.value + userMessage

        viewModelScope.launch {
            _isLoading.value = true
            val result = geminiRepository.generateResponse(trimmed)
            _isLoading.value = false

            val botText = result.getOrElse { error ->
                "Desculpe, não foi possível obter uma resposta no momento (${error.message ?: "Erro desconhecido"})."
            }
            val botMessage = ChatMessage(text = botText, isUser = false)
            _messages.value = _messages.value + botMessage
        }
    }
}
