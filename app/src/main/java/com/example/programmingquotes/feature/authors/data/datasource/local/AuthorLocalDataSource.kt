package com.example.programmingquotes.feature.authors.data.datasource.local

import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

internal interface AuthorLocalDataSource {

    suspend fun insertAuthors(authors: List<AuthorEntity>)

    fun getAuthors(): Flow<List<AuthorEntity>>

    fun getRandomQuote(): QuoteEntity
}