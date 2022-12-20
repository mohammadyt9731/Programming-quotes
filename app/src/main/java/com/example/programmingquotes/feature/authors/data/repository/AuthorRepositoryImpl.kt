package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSource
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class AuthorRepositoryImpl @Inject constructor(
    private val localDataSource: AuthorLocalDataSource,
    private val remoteDataSource: AuthorRemoteDataSource,
) :
    AuthorRepository {

    override fun getRandomQuote(): Flow<QuoteView?> =
        localDataSource.getRandomQuote().map { it?.toQuoteView() }

    override fun getAuthors(): Flow<List<AuthorView>> =
        localDataSource.getAuthors().map {
            it.map { authorEntity ->
                authorEntity.toAuthorView()
            }
        }

    override suspend fun fetchRandomQuote(): ResultWrapper<QuoteView> {
        return safeApiCall {
            remoteDataSource.fetchRandomQuote().toQuoteView()
        }
    }

    override suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<List<AuthorView>> =
        safeApiCall {
            val response = remoteDataSource.fetchAuthors()
            localDataSource.insertAuthors(
                response.values.toList().map { authorResponse ->
                    authorResponse.toAuthorEntity()
                }
            )
            response.values.map { it.toAuthorView() }
        }

    override suspend fun getAuthors(
        isRefresh: Boolean
    ): Flow<ResultWrapper<List<AuthorView>>> =
        flow {
            getAuthors()
                .onStart {
                    if (isRefresh) {
                        emit(fetchAuthors())
                    }
                }
                .collect {
                    emit(ResultWrapper.Success(it))
                    if (it.isEmpty())
                        emit(fetchAuthors())
                }
        }

    override suspend fun fetchAuthors(): ResultWrapper<List<AuthorView>> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthors()
            localDataSource.insertAuthors(
                response.values.toList().map { authorResponse ->
                    authorResponse.toAuthorEntity()
                }
            )
            response.values.map { it.toAuthorView() }
        }
    }
}