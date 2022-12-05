package com.example.programmingquotes.feature.quote.data.datasource.remote

import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse

interface QuoteRemoteDataSource {

    suspend fun getAuthorQuotes(authorName: String): AuthorWithQuotesResponse?
}