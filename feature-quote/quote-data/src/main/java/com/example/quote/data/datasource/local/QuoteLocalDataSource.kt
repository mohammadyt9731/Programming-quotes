package com.example.quote.data.datasource.local

import com.example.quote.model.AuthorWithQuotes
import com.example.quote.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

internal interface QuoteLocalDataSource {
    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
    fun getRandomQuote(): QuoteEntity?
}