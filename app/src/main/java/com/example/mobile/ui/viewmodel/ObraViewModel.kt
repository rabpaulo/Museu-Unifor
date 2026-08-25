package com.example.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.model.Obra
import com.example.mobile.data.repository.ObraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ObraViewModel(
    private val obraRepository: ObraRepository = ObraRepository()
) : ViewModel() {

    private val _obras = MutableStateFlow<List<Obra>>(emptyList())
    val obras: StateFlow<List<Obra>> = _obras.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedObra = MutableStateFlow<Obra?>(null)
    val selectedObra: StateFlow<Obra?> = _selectedObra.asStateFlow()

    init {
        loadAllObras()
    }

    fun loadAllObras() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _obras.value = obraRepository.getAllObras()
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar obras: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadObraDetails(autorId: String, obraId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _selectedObra.value = obraRepository.getObra(autorId, obraId)
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar detalhes da obra: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addObra(
        autorId: String,
        autorNome: String,
        nome: String,
        data: String,
        descricao: String,
        image: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val obra = Obra(
                autorId = autorId,
                autor = autorNome,
                nome = nome,
                data = data,
                descricao = descricao,
                image = image
            )
            val result = obraRepository.addObra(autorId, obra)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAllObras()
                onComplete(true, "Obra adicionada com sucesso!")
            } else {
                onComplete(false, "Erro ao adicionar obra: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun updateObra(
        autorId: String,
        obraId: String,
        autorNome: String,
        nome: String,
        data: String,
        descricao: String,
        image: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val obra = Obra(
                id = obraId,
                autorId = autorId,
                autor = autorNome,
                nome = nome,
                data = data,
                descricao = descricao,
                image = image
            )
            val result = obraRepository.updateObra(autorId, obraId, obra)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAllObras()
                onComplete(true, "Obra atualizada com sucesso!")
            } else {
                onComplete(false, "Erro ao atualizar Obra: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun deleteObra(
        autorId: String,
        obraId: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = obraRepository.deleteObra(autorId, obraId)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAllObras()
                onComplete(true, "Obra deletada com sucesso!")
            } else {
                onComplete(false, "Erro ao deletar obra: ${result.exceptionOrNull()?.message}")
            }
        }
    }
}
