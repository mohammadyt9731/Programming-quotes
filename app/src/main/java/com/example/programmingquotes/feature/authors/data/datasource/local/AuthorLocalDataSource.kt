package com.example.programmingquotes.feature.authors.data.datasource.local

import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import kotlinx.coroutines.flow.Flow

interface AuthorLocalDataSource {

    suspend fun insertAuthors(authors: List<AuthorEntity>)

    fun getAuthors(): Flow<List<AuthorEntity>>
}