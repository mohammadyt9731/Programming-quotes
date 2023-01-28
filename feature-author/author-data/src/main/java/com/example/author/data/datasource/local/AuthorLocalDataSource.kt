package com.example.author.data.datasource.local

import com.example.author.model.AuthorEntity
import kotlinx.coroutines.flow.Flow

internal interface AuthorLocalDataSource {

    suspend fun insertAuthors(authors: List<AuthorEntity>)
    fun getAuthors(): Flow<List<AuthorEntity>>
}