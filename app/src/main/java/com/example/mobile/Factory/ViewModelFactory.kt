package com.example.mobile.Factory

import com.example.mobile.Model.AutorModel
import com.example.mobile.Model.GeminiView
import com.example.mobile.Model.ObraModel

class ViewModelFactory{
    fun CreateViewModel(type: String): Any {
        return when (type) {
            "Gemini" -> GeminiView()
            "Obra" -> ObraModel()
            "Autor" -> AutorModel()
             else -> throw IllegalArgumentException("Unknown ViewModel type: $type")
        }
    }
}