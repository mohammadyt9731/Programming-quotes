package com.example.quote_data.datasource.remote


import com.example.common.android.bodyOrThrow
import com.example.quote_data.network.QuoteApi
import com.example.quote_model.AuthorWithQuotesResponse
import com.example.quote_model.QuoteResponse
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