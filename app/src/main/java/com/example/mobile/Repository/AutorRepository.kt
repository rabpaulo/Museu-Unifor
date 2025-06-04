package com.example.mobile.Repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AutorRepository(private val db: FirebaseFirestore) {
    suspend fun cadastrarAutor(context: Context, autorData: Map<String, String>): Boolean {
        return try {
            db.collection("autor")
                .add(autorData)
                .await()
            Toast.makeText(context, "Autor adicionado com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao adicionar autor: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }

    suspend fun editarAutor(context: Context, id: String, autorData: Map<String, String>): Boolean {
        return try {
            db.collection("autor")
                .document(id)
                .update(autorData)
                .await()
            Toast.makeText(context, "Autor atualizado com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao atualizar autor: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }

    suspend fun deletarAutor(context: Context, id: String): Boolean {
        return try {
            val autorRef = db.collection("autor").document(id)
            val obrasRef = autorRef.collection("obras")

            val batch = db.batch()

            val obrasSnapshot = obrasRef.get().await()
            for (document in obrasSnapshot) {
                batch.delete(document.reference)
            }

            batch.delete(autorRef)
            batch.commit().await()

            Toast.makeText(context, "Autor e suas obras deletados com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao deletar autor e suas obras: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }
}
