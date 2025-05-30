package com.example.mobile.Models

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AutorViewModel:ViewModel() {
    var nome by mutableStateOf("")
    var descricao by mutableStateOf("")
    var date by mutableStateOf("")
    var image by mutableStateOf("")

    var fb = Firebase.firestore

    fun cadastrarAutor(context: Context){
        fb.collection("autor")
            .add(
                mapOf(
                "nome" to nome,
                "descricao" to descricao,
                "data" to date,
                "image" to image,
            )
        ).addOnSuccessListener {
                Toast.makeText(context, "Autor adicionado com sucesso!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Erro ao adicionar autor: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    // Update Author in Firestore
    fun EditarAutor(context: Context, id: String) {
        val data = mapOf(
            "nome" to nome,
            "data" to date,
            "descricao" to descricao,
            "image" to image
        )

        fb.collection("autor").document(id)
            .update(data)
            .addOnSuccessListener {
                Toast.makeText(context, "Autor atualizado com sucesso!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao atualizar autor: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun DeletarAutor(context: Context, id: String){
        val autorRef = fb.collection("autor").document(id)
        val obrasRef = autorRef.collection("obras")

        val batch = fb.batch()

        obrasRef
            .get()
            .addOnSuccessListener {
            querySnapshot ->
            for (document in querySnapshot) {
                batch.delete(document.reference)
            }

            // Then, delete the 'autor' document itself
            batch.delete(autorRef)

            // Commit the batch operation
            batch.commit()
                .addOnSuccessListener {
                    Toast.makeText(context, "Autor e suas obras deletados com sucesso!", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Falha ao deletar autor e suas obras", Toast.LENGTH_LONG).show()
                }
        }.addOnFailureListener {
            Toast.makeText(context, "Falha ao carregar obras para deletar", Toast.LENGTH_LONG).show()
        }
    }
}