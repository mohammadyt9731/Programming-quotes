package com.example.author.data.network

import com.example.author.model.AuthorResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface AuthorApi {
    @GET("Authors")
    suspend fun getAuthors(): Response<Map<String, AuthorResponse>>
}