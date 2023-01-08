package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSource
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.authors.domain.AuthorRepository
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AuthorRepositoryImpl @Inject constructor(
    private val localDataSource: AuthorLocalDataSource,
    private val remoteDataSource: AuthorRemoteDataSource,
) :
    AuthorRepository {

    override fun getRandomQuote(): Flow<ResultWrapper<QuoteResponse>> =
        flow {
            val response = fetchRandomQuote()
            if (response is ResultWrapper.Success) {
                emit(response)
            } else {
                val result = getRandomQuoteFromDb()
                if (result == null) {
                    emit(response)
                    emit(ResultWrapper.UnInitialize)
                } else {
                    emit(ResultWrapper.Success(result.toQuoteResponse()))
                }
            }
        }

    private suspend fun fetchRandomQuote(): ResultWrapper<QuoteResponse> {
        return safeApiCall {
            remoteDataSource.fetchRandomQuote()
        }
    }

    private fun getRandomQuoteFromDb() = localDataSource.getRandomQuote()

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