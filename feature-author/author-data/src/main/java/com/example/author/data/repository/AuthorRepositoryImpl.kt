package com.example.author.data.repository

import com.example.author.data.datasource.local.AuthorLocalDataSource
import com.example.author.data.datasource.remote.AuthorRemoteDataSource
import com.example.author.domain.AuthorRepository
import com.example.author.model.AuthorEntity
import com.example.base.ResultWrapper
import com.example.common.android.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthorRepositoryImpl @Inject constructor(
    private val localDataSource: AuthorLocalDataSource,
    private val remoteDataSource: AuthorRemoteDataSource,
) :
    AuthorRepository {
    override fun getAuthors(): Flow<List<AuthorEntity>> = localDataSource.getAuthors()
    override suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthors()
            localDataSource.insertAuthors(response.values.toList().map { authorResponse ->
                authorResponse.toAuthorEntity()
            })
        }
    }
}