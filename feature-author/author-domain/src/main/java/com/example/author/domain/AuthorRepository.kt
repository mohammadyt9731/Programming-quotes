package com.example.author.domain

import com.example.author.model.AuthorEntity
import com.example.base.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun getAuthors(): Flow<List<AuthorEntity>>
    suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit>
}