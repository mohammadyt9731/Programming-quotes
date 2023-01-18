package com.example.quote_domain

import com.example.common.ResultWrapper
import com.example.quote_model.AuthorWithQuotes
import com.example.quote_model.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<Unit>
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
    fun getRandomQuote(): Flow<ResultWrapper<QuoteResponse>>
}