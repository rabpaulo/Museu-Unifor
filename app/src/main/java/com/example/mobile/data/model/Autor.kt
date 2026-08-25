package com.example.mobile.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class Autor(
    val id: String = "",
    val nome: String = "",
    val data: String = "",
    val descricao: String = "",
    val image: String = ""
) {
    fun toMap(): Map<String, String> = mapOf(
        "nome" to nome,
        "data" to data,
        "descricao" to descricao,
        "image" to image
    )

    companion object {
        fun fromDocument(doc: DocumentSnapshot): Autor {
            return Autor(
                id = doc.id,
                nome = doc.getString("nome").orEmpty(),
                data = doc.getString("data").orEmpty(),
                descricao = doc.getString("descricao").orEmpty(),
                image = doc.getString("image").orEmpty()
            )
        }
    }
}
