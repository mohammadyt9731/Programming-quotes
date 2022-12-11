package com.example.programmingquotes.feature.authors.data.datasource.remote

import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse

interface AuthorRemoteDataSource {

    suspend fun getAuthors(): Map<String, AuthorResponse>?

    suspend fun getRandomQuote(): QuoteResponse?
}