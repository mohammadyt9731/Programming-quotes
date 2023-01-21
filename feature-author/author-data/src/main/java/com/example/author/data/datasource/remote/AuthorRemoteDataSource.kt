package com.example.author.data.datasource.remote

import com.example.author.model.AuthorResponse

internal interface AuthorRemoteDataSource {
    suspend fun fetchAuthors(): Map<String, AuthorResponse>
}