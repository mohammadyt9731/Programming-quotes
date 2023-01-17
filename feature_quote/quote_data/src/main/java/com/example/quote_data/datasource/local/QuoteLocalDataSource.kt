package com.example.quote_data.datasource.local

import com.example.quote_model.AuthorWithQuotes
import com.example.quote_model.QuoteEntity
import kotlinx.coroutines.flow.Flow

internal interface QuoteLocalDataSource {

    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)

    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>

    fun getRandomQuote(): QuoteEntity?
}