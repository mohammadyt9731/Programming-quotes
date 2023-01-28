package com.example.quote.domain

import com.example.base.ResultWrapper
import com.example.quote.model.AuthorWithQuotes
import com.example.quote.model.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<Unit>
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
    fun getRandomQuote(): Flow<ResultWrapper<QuoteResponse>>
}