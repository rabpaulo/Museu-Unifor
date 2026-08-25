package com.example.mobile.data.repository

import com.example.mobile.data.mock.MockData
import com.example.mobile.data.model.Autor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AutorRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    // In-memory cache initialized with MockData
    private val localAutores = MockData.autores.toMutableList()

    suspend fun getAutores(): List<Autor> {
        return try {
            val snapshot = db.collection("autor").get().await()
            if (snapshot.isEmpty) {
                localAutores
            } else {
                val remote = snapshot.documents.map { Autor.fromDocument(it) }
                // Merge remote with local if any unique
                val remoteIds = remote.map { it.id }.toSet()
                remote + localAutores.filter { it.id !in remoteIds }
            }
        } catch (e: Exception) {
            localAutores
        }
    }

    suspend fun getAutor(id: String): Autor? {
        return try {
            val doc = db.collection("autor").document(id).get().await()
            if (doc.exists()) {
                Autor.fromDocument(doc)
            } else {
                localAutores.find { it.id == id }
            }
        } catch (e: Exception) {
            localAutores.find { it.id == id }
        }
    }

    suspend fun getAutorIdByName(nome: String): String? {
        return try {
            val query = db.collection("autor")
                .whereEqualTo("nome", nome)
                .get()
                .await()
            query.documents.firstOrNull()?.id ?: localAutores.find { it.nome.equals(nome, ignoreCase = true) }?.id
        } catch (e: Exception) {
            localAutores.find { it.nome.equals(nome, ignoreCase = true) }?.id
        }
    }

    suspend fun addAutor(autor: Autor): Result<String> {
        val generatedId = if (autor.id.isNotBlank()) autor.id else "autor_${UUID.randomUUID().toString().take(8)}"
        val newAutor = autor.copy(id = generatedId)
        localAutores.add(0, newAutor)

        return try {
            val docRef = db.collection("autor").add(newAutor.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            // Local fallback success
            Result.success(generatedId)
        }
    }

    suspend fun updateAutor(autor: Autor): Result<Unit> {
        val index = localAutores.indexOfFirst { it.id == autor.id }
        if (index != -1) {
            localAutores[index] = autor
        } else {
            localAutores.add(autor)
        }

        return try {
            db.collection("autor").document(autor.id).update(autor.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    suspend fun deleteAutor(id: String): Result<Unit> {
        localAutores.removeAll { it.id == id }

        return try {
            val autorRef = db.collection("autor").document(id)
            val obrasRef = autorRef.collection("obras")
            val batch = db.batch()

            val obrasSnapshot = obrasRef.get().await()
            for (doc in obrasSnapshot.documents) {
                batch.delete(doc.reference)
            }
            batch.delete(autorRef)
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }
}
