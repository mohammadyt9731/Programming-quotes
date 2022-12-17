package com.example.programmingquotes.feature.authors.data.datasource.remote

import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse

internal interface AuthorRemoteDataSource {

    suspend fun fetchAuthors(): Map<String, AuthorResponse>

    suspend fun fetchRandomQuote(): QuoteResponse
}