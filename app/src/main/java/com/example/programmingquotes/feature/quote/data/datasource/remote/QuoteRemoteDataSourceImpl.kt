package com.example.programmingquotes.feature.quote.data.datasource.remote

import com.example.programmingquotes.core.data.network.bodyOrThrow
import com.example.programmingquotes.feature.quote.data.network.QuoteApi
import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse
import javax.inject.Inject

class QuoteRemoteDataSourceImpl @Inject constructor(
    private val quoteApi: QuoteApi
) : QuoteRemoteDataSource {

    override suspend fun getAuthorQuotes(authorName: String): AuthorWithQuotesResponse? {
        return quoteApi.getAuthorQuotes(authorName = authorName).bodyOrThrow()
    }
}