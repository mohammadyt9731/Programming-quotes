package com.example.author_domain

import com.example.author_model.AuthorEntity
import com.example.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun getAuthors(): Flow<List<AuthorEntity>>
    suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit>
}