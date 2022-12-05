package com.example.programmingquotes.feature.quote.data.network

import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuoteApi {

    @GET("Authors/{authorName}")
    suspend fun getAuthorQuotes(@Path("authorName") authorName: String): Response<AuthorWithQuotesResponse?>
}