package com.example.programmingquotes.feature.authors.data.datasource.remote

import com.example.programmingquotes.core.data.network.bodyOrThrow
import com.example.programmingquotes.feature.authors.data.network.AuthorApi
import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import javax.inject.Inject

class AuthorRemoteDataSourceImpl @Inject constructor(
    private val authorApi: AuthorApi
) : AuthorRemoteDataSource {

    override suspend fun getAuthors(): Map<String, AuthorResponse>? {
        return authorApi.getAuthors().bodyOrThrow()
    }

    override suspend fun getRandomQuote(): QuoteResponse? {
        return authorApi.getRandomQuote().bodyOrThrow()
    }
}