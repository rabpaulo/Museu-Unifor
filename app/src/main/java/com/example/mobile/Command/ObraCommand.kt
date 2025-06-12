package com.example.mobile.Command

import android.content.Context
import com.example.mobile.Repository.ObraRepository

class ObraCommand( private val context: Context, private val idAutor: String, private val repo: ObraRepository, private val obra: Map<String, String>): Command {
    override suspend fun execute() {
        repo.adicionarObra(context, idAutor, obra)
    }
}