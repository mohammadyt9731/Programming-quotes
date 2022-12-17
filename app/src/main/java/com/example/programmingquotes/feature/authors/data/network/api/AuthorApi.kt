package com.example.programmingquotes.feature.authors.data.network.api

import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface AuthorApi {

    @GET("Quotes/random")
    suspend fun getRandomQuote(): Response<QuoteResponse>

    @GET("Authors")
    suspend fun getAuthors(): Response<Map<String, AuthorResponse>>
}