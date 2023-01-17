package com.example.quote_data.datasource.remote

import com.example.quote_model.AuthorWithQuotesResponse
import com.example.quote_model.QuoteResponse

internal interface QuoteRemoteDataSource {
    suspend fun fetchAuthorWithQuotes(authorName: String): AuthorWithQuotesResponse

    suspend fun fetchRandomQuote(): QuoteResponse
}