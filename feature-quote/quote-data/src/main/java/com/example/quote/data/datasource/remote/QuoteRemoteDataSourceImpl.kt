package com.example.quote.data.datasource.remote

import com.example.common.android.bodyOrThrow
import com.example.quote.data.network.QuoteApi
import com.example.quote.model.AuthorWithQuotesResponse
import com.example.quote.model.QuoteResponse
import javax.inject.Inject

internal class QuoteRemoteDataSourceImpl @Inject constructor(
    private val api: QuoteApi
) : QuoteRemoteDataSource {
    override suspend fun fetchAuthorWithQuotes(authorName: String): AuthorWithQuotesResponse {
        return api.getAuthorWithQuotes(authorName = authorName).bodyOrThrow()
    }

    override suspend fun fetchRandomQuote(): QuoteResponse =
        api.getRandomQuote().bodyOrThrow()
}