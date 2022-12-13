package com.example.programmingquotes.feature.quote.data.datasource.remote

import com.example.programmingquotes.core.data.network.bodyOrThrow
import com.example.programmingquotes.feature.quote.data.network.api.QuoteApi
import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse
import javax.inject.Inject

class QuoteRemoteDataSourceImpl @Inject constructor(
    private val api: QuoteApi
) : QuoteRemoteDataSource {

    override suspend fun fetchAuthorWithQuotes(authorName: String): AuthorWithQuotesResponse? {
        return api.getAuthorWithQuotes(authorName = authorName).bodyOrThrow()
    }
}