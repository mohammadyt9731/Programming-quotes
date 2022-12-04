package com.example.programmingquotes.feature.quote.data.network

import com.example.programmingquotes.feature.quote.data.network.model.QuotesWithAuthorResponse
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApi {

    @GET("Authors/{authorName}")
    suspend fun getAuthorQuotes(authorName: String): Response<List<QuotesWithAuthorResponse>?>
}