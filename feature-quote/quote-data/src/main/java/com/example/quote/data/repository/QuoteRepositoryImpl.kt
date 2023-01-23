package com.example.quote.data.repository

import com.example.base.ResultWrapper
import com.example.common.android.safeApiCall
import com.example.quote.data.datasource.local.QuoteLocalDataSource
import com.example.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.quote.domain.QuoteRepository
import com.example.quote.model.AuthorWithQuotes
import com.example.quote.model.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class QuoteRepositoryImpl @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {
    override suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<Unit> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthorWithQuotes(authorName)

            localDataSource.insertAuthorQuotes(
                response.quotes.map { quoteResponse ->
                    quoteResponse.toQuoteEntity()
                }
            )
        }
    }

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes> =
        localDataSource.getAuthorWithQuotes(authorName)

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
}