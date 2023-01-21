package com.example.author.data.datasource.remote

import com.example.author.data.network.AuthorApi
import com.example.author.model.AuthorResponse
import com.example.common.android.bodyOrThrow
import javax.inject.Inject

internal class AuthorRemoteDataSourceImpl @Inject constructor(
    private val authorApi: AuthorApi
) : AuthorRemoteDataSource {
    override suspend fun fetchAuthors(): Map<String, AuthorResponse> =
        authorApi.getAuthors().bodyOrThrow()
}