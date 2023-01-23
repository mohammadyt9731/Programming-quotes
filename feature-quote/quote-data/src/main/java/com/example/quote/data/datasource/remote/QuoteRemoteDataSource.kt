package com.example.quote.data.datasource.remote

import com.example.quote.model.AuthorWithQuotesResponse
import com.example.quote.model.QuoteResponse


internal interface QuoteRemoteDataSource {
    suspend fun fetchAuthorWithQuotes(authorName: String): AuthorWithQuotesResponse
    suspend fun fetchRandomQuote(): QuoteResponse
}