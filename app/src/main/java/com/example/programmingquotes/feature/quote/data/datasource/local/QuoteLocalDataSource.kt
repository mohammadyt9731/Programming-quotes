package com.example.programmingquotes.feature.quote.data.datasource.local

import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow

interface QuoteLocalDataSource {

    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)

    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}