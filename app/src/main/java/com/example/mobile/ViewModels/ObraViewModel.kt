package com.example.mobile.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ObraViewModel:ViewModel() {
    var autor by mutableStateOf("")
    var nome by mutableStateOf("")
    var data by mutableStateOf("")
    var descricao by mutableStateOf("")
    var image by mutableStateOf("")

    val db = FirebaseFirestore.getInstance()
    fun adicionarObra(context: Context, id: String){
        db.collection("autor")
            .document(id)
            .collection("obras")
            .add(
                mapOf(
                    "autor" to autor,
                    "nome" to nome,
                    "data" to data,
                    "descricao" to descricao,
                    "image" to image
                )
            ).addOnSuccessListener {
                Toast.makeText(context, "Obra adicionada com sucesso!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Erro ao adicionar obra: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun EditarObra(context: Context, idAutor: String, idObra: String){
        val data = mapOf(
            "autor" to autor,
            "nome" to nome,
            "data" to data,
            "descricao" to descricao,
            "image" to image
        )
        db.collection("autor")
            .document(idAutor)
            .collection("obras")
            .document(idObra)
            .update(data)
            .addOnSuccessListener {
                Toast.makeText(context, "Obra atualizada com sucesso!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao atualizar Obra: ${e.message}", Toast.LENGTH_LONG).show()
            }

    }

    fun DeletarObra(context: Context, idAutor: String, idObra: String){
        db.collection("autor")
            .document(idAutor)
            .collection("obras")
            .document(idObra)
            .delete()
            .addOnSuccessListener{
                Toast.makeText(context, "Obra deletada com sucesso!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                    exception ->
                Toast.makeText(context, "Erro ao deletar obra obra: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }
}
