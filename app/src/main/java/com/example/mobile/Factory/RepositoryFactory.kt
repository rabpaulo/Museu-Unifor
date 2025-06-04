package com.example.mobile.Factory

import com.example.mobile.Repository.ObraRepository
import com.example.mobile.Repository.AutorRepository
import com.google.firebase.firestore.FirebaseFirestore

class RepositoryFactory {
    fun CreateRepository(type: String): Any {
        return when (type) {
            "Obra" -> ObraRepository(FirebaseFirestore.getInstance())
            "Autor" -> AutorRepository(FirebaseFirestore.getInstance())
            else -> throw IllegalArgumentException("Unknown Repository type: $type")
        }
    }
}