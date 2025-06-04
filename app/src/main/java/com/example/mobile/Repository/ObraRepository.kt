package com.example.mobile.Repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ObraRepository(private val db: FirebaseFirestore) {
    suspend fun adicionarObra(context: Context, idAutor: String, obraData: Map<String, String>): Boolean {
        return try {
            db.collection("autor")
                .document(idAutor)
                .collection("obras")
                .add(obraData)
                .await()
            Toast.makeText(context, "Obra adicionada com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao adicionar obra: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }

    suspend fun editarObra(context: Context, idAutor: String, idObra: String, obraData: Map<String, String>): Boolean {
        return try {
            db.collection("autor")
                .document(idAutor)
                .collection("obras")
                .document(idObra)
                .update(obraData)
                .await()
            Toast.makeText(context, "Obra atualizada com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao atualizar obra: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }

    suspend fun deletarObra(context: Context, idAutor: String, idObra: String): Boolean {
        return try {
            db.collection("autor")
                .document(idAutor)
                .collection("obras")
                .document(idObra)
                .delete()
                .await()
            Toast.makeText(context, "Obra deletada com sucesso!", Toast.LENGTH_LONG).show()
            true
        } catch (exception: Exception) {
            Toast.makeText(context, "Erro ao deletar obra: ${exception.message}", Toast.LENGTH_LONG).show()
            false
        }
    }
}
