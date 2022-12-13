package com.example.programmingquotes.feature.authors.data.datasource.remote

import com.example.programmingquotes.core.data.network.bodyOrThrow
import com.example.programmingquotes.feature.authors.data.network.api.AuthorApi
import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import javax.inject.Inject

class AuthorRemoteDataSourceImpl @Inject constructor(
    private val authorApi: AuthorApi
) : AuthorRemoteDataSource {

    override suspend fun fetchAuthors(): Map<String, AuthorResponse>? =
        authorApi.getAuthors().bodyOrThrow()

    override suspend fun fetchRandomQuote(): QuoteResponse? =
        authorApi.getRandomQuote().bodyOrThrow()

}