package com.example.quote_data.network

import com.example.quote_model.AuthorWithQuotesResponse
import com.example.quote_model.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface QuoteApi {

    @GET("Authors/{authorName}")
    suspend fun getAuthorWithQuotes(@Path("authorName") authorName: String):
            Response<AuthorWithQuotesResponse>

    @GET("Quotes/random")
    suspend fun getRandomQuote(): Response<QuoteResponse>
}