package com.example.author_data.datasource.remote

import com.example.author_data.network.AuthorApi
import com.example.author_model.AuthorResponse
import com.example.common_android.bodyOrThrow
import javax.inject.Inject

internal class AuthorRemoteDataSourceImpl @Inject constructor(
    private val authorApi: AuthorApi
) : AuthorRemoteDataSource {
    override suspend fun fetchAuthors(): Map<String, AuthorResponse> =
        authorApi.getAuthors().bodyOrThrow()
}