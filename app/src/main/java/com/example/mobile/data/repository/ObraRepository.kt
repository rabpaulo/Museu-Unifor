package com.example.mobile.data.repository

import com.example.mobile.data.mock.MockData
import com.example.mobile.data.model.Obra
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class ObraRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    // In-memory cache initialized with MockData
    private val localObras = MockData.obras.toMutableList()

    suspend fun getAllObras(): List<Obra> {
        return try {
            val authorsSnapshot = db.collection("autor").get().await()
            if (authorsSnapshot.isEmpty) {
                localObras
            } else {
                val remoteObras = mutableListOf<Obra>()
                for (authorDoc in authorsSnapshot.documents) {
                    val authorId = authorDoc.id
                    val obrasSnapshot = db.collection("autor")
                        .document(authorId)
                        .collection("obras")
                        .get()
                        .await()
                    for (obraDoc in obrasSnapshot.documents) {
                        remoteObras.add(Obra.fromDocument(obraDoc, authorId))
                    }
                }
                if (remoteObras.isEmpty()) {
                    localObras
                } else {
                    val remoteIds = remoteObras.map { it.id }.toSet()
                    remoteObras + localObras.filter { it.id !in remoteIds }
                }
            }
        } catch (e: Exception) {
            localObras
        }
    }

    suspend fun getObrasByAutor(autorId: String): List<Obra> {
        return try {
            val snapshot = db.collection("autor")
                .document(autorId)
                .collection("obras")
                .get()
                .await()
            if (snapshot.isEmpty) {
                localObras.filter { it.autorId == autorId }
            } else {
                val remote = snapshot.documents.map { Obra.fromDocument(it, autorId) }
                val remoteIds = remote.map { it.id }.toSet()
                remote + localObras.filter { it.autorId == autorId && it.id !in remoteIds }
            }
        } catch (e: Exception) {
            localObras.filter { it.autorId == autorId }
        }
    }

    suspend fun getObra(autorId: String, obraId: String): Obra? {
        return try {
            val doc = db.collection("autor")
                .document(autorId)
                .collection("obras")
                .document(obraId)
                .get()
                .await()
            if (doc.exists()) {
                Obra.fromDocument(doc, autorId)
            } else {
                localObras.find { it.id == obraId }
            }
        } catch (e: Exception) {
            localObras.find { it.id == obraId }
        }
    }

    suspend fun addObra(autorId: String, obra: Obra): Result<String> {
        val generatedId = if (obra.id.isNotBlank()) obra.id else "obra_${UUID.randomUUID().toString().take(8)}"
        val newObra = obra.copy(id = generatedId, autorId = autorId)
        localObras.add(0, newObra)

        return try {
            val docRef = db.collection("autor")
                .document(autorId)
                .collection("obras")
                .add(newObra.toMap())
                .await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.success(generatedId)
        }
    }

    suspend fun updateObra(autorId: String, obraId: String, obra: Obra): Result<Unit> {
        val index = localObras.indexOfFirst { it.id == obraId }
        val updated = obra.copy(id = obraId, autorId = autorId)
        if (index != -1) {
            localObras[index] = updated
        } else {
            localObras.add(updated)
        }

        return try {
            db.collection("autor")
                .document(autorId)
                .collection("obras")
                .document(obraId)
                .update(updated.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    suspend fun deleteObra(autorId: String, obraId: String): Result<Unit> {
        localObras.removeAll { it.id == obraId }

        return try {
            db.collection("autor")
                .document(autorId)
                .collection("obras")
                .document(obraId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }
}
