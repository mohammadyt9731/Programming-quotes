package com.example.programmingquotes.feature.authors.data.datasource.remote

import com.example.author_data.network.api.AuthorApi
import com.example.author_model.AuthorResponse
import com.example.programmingquotes.core.data.network.bodyOrThrow
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import javax.inject.Inject

internal class AuthorRemoteDataSourceImpl @Inject constructor(
    private val authorApi: AuthorApi
) : AuthorRemoteDataSource {

    override suspend fun fetchAuthors(): Map<String, AuthorResponse> =
        authorApi.getAuthors().bodyOrThrow()

    override suspend fun fetchRandomQuote(): QuoteResponse =
        authorApi.getRandomQuote().bodyOrThrow()

}