package com.example.programmingquotes.feature.quote.data.datasource.remote

import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse

internal interface QuoteRemoteDataSource {
    suspend fun fetchAuthorWithQuotes(authorName: String): AuthorWithQuotesResponse
}