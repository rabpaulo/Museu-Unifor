package com.example.mobile.data.model

import com.google.firebase.firestore.DocumentSnapshot

data class Obra(
    val id: String = "",
    val autorId: String = "",
    val autor: String = "",
    val nome: String = "",
    val data: String = "",
    val descricao: String = "",
    val image: String = ""
) {
    fun toMap(): Map<String, String> = mapOf(
        "autor" to autor,
        "nome" to nome,
        "data" to data,
        "descricao" to descricao,
        "image" to image
    )

    companion object {
        fun fromDocument(doc: DocumentSnapshot, autorId: String = ""): Obra {
            return Obra(
                id = doc.id,
                autorId = autorId,
                autor = doc.getString("autor").orEmpty(),
                nome = doc.getString("nome").orEmpty(),
                data = doc.getString("data").orEmpty(),
                descricao = doc.getString("descricao").orEmpty(),
                image = doc.getString("image").orEmpty()
            )
        }
    }
}
