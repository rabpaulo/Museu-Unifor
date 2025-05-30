package com.example.mobile.Factory

import com.example.mobile.Models.AutorViewModel
import com.example.mobile.Models.GeminiViewModel
import com.example.mobile.Models.ObraViewModel

class ViewModelFactory{
    fun CreateViewModel(type: String): Any {
        return when (type) {
            "Gemini" -> GeminiViewModel()
            "Obra" -> ObraViewModel()
            "Autor" -> AutorViewModel()
             else -> throw IllegalArgumentException("Unknown ViewModel type: $type")
        }
    }
}