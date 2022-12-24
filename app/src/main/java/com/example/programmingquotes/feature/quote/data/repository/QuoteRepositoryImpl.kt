package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

internal class QuoteRepositoryImpl @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {

    private suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<AuthorWithQuotesView> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthorWithQuotes(authorName)

            localDataSource.insertAuthorQuotes(
                response.quotes.map { quoteResponse ->
                    quoteResponse.toQuoteEntity()
                }
            )

            response.toAuthorWithQuotesView()
        }
    }

    override fun getAuthorWithQuotes(
        authorName: String,
        isRefresh: Boolean
    ): Flow<ResultWrapper<AuthorWithQuotesView>> =
        flow {
            getAuthorWithQuotesFromDb(authorName)
                .onStart {
                    if (isRefresh) {
                        emit(fetchAuthorQuotesAndInsertToDb(authorName))
                    }
                }.collect {
                    emit(ResultWrapper.Success(it))
                    if (it.quotes.isEmpty()) {
                        val response = fetchAuthorQuotesAndInsertToDb(authorName)
                        if (response is ResultWrapper.Error) {
                            emit(response)
                        }
                    }
                }
        }

    override fun getAuthorWithQuotesFromDb(authorName: String): Flow<AuthorWithQuotesView> =
        localDataSource.getAuthorWithQuotes(authorName).map { it.toAuthorWithQuotesView() }
}