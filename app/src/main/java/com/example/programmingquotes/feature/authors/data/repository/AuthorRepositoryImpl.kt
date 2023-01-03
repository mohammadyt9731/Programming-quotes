package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSource
import com.example.programmingquotes.feature.authors.ui.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AuthorRepositoryImpl @Inject constructor(
    private val localDataSource: AuthorLocalDataSource,
    private val remoteDataSource: AuthorRemoteDataSource,
) :
    AuthorRepository {

    override fun getRandomQuote(): Flow<ResultWrapper<QuoteView?>> =
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
                    emit(ResultWrapper.Success(result))
                }
            }
        }

    private suspend fun fetchRandomQuote(): ResultWrapper<QuoteView> {
        return safeApiCall {
            remoteDataSource.fetchRandomQuote().toQuoteView()
        }
    }

    private fun getRandomQuoteFromDb(): QuoteView? = localDataSource.getRandomQuote()?.toQuoteView()

    override fun getAuthors(): Flow<List<AuthorView>> =
        localDataSource.getAuthors().map { it.map { authorEntity -> authorEntity.toAuthorView() } }

    override suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthors()
            localDataSource.insertAuthors(response.values.toList().map { authorResponse ->
                authorResponse.toAuthorEntity()
            })
        }
    }
}