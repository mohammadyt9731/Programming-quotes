package com.example.author_data.datasource.remote

import com.example.author_model.AuthorResponse

internal interface AuthorRemoteDataSource {
    suspend fun fetchAuthors(): Map<String, AuthorResponse>
}