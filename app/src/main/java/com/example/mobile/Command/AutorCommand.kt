package com.example.mobile.Command

import com.example.mobile.Repository.AutorRepository
import android.content.Context

class AutorCommand(private val context: Context, private val repo: AutorRepository, private val autor: Map<String, String>): Command{
    override suspend fun execute() {
        repo.cadastrarAutor(context, autor)
    }
}