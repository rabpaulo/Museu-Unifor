package com.example.mobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.model.Autor
import com.example.mobile.data.model.Obra
import com.example.mobile.data.repository.AutorRepository
import com.example.mobile.data.repository.ObraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AutorViewModel(
    private val autorRepository: AutorRepository = AutorRepository(),
    private val obraRepository: ObraRepository = ObraRepository()
) : ViewModel() {

    private val _autores = MutableStateFlow<List<Autor>>(emptyList())
    val autores: StateFlow<List<Autor>> = _autores.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedAutor = MutableStateFlow<Autor?>(null)
    val selectedAutor: StateFlow<Autor?> = _selectedAutor.asStateFlow()

    private val _authorObras = MutableStateFlow<List<Obra>>(emptyList())
    val authorObras: StateFlow<List<Obra>> = _authorObras.asStateFlow()

    init {
        loadAutores()
    }

    fun loadAutores() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _autores.value = autorRepository.getAutores()
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar autores: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAutorDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _selectedAutor.value = autorRepository.getAutor(id)
                _authorObras.value = obraRepository.getObrasByAutor(id)
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar dados do autor: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addAutor(
        nome: String,
        data: String,
        descricao: String,
        image: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val autor = Autor(nome = nome, data = data, descricao = descricao, image = image)
            val result = autorRepository.addAutor(autor)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAutores()
                onComplete(true, "Autor adicionado com sucesso!")
            } else {
                onComplete(false, "Erro ao adicionar autor: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun updateAutor(
        id: String,
        nome: String,
        data: String,
        descricao: String,
        image: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            val autor = Autor(id = id, nome = nome, data = data, descricao = descricao, image = image)
            val result = autorRepository.updateAutor(autor)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAutores()
                onComplete(true, "Autor atualizado com sucesso!")
            } else {
                onComplete(false, "Erro ao atualizar autor: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun deleteAutor(id: String, onComplete: (success: Boolean, message: String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = autorRepository.deleteAutor(id)
            _isLoading.value = false
            if (result.isSuccess) {
                loadAutores()
                onComplete(true, "Autor e suas obras deletados com sucesso!")
            } else {
                onComplete(false, "Falha ao deletar autor e suas obras")
            }
        }
    }

    suspend fun getAutorIdByName(nome: String): String? {
        return autorRepository.getAutorIdByName(nome)
    }
}
