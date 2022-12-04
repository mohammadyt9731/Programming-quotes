package com.example.programmingquotes.feature.quote.data.datasource.remote

import com.example.programmingquotes.feature.quote.data.network.model.QuotesWithAuthorResponse

interface QuoteRemoteDataSource {

    suspend fun getAuthorQuotes(authorName: String): List<QuotesWithAuthorResponse>?
}