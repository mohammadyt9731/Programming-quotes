package com.example.quote_data.repository

import com.example.common.ResultWrapper
import com.example.common_android.safeApiCall
import com.example.quote_data.datasource.local.QuoteLocalDataSource
import com.example.quote_data.datasource.remote.QuoteRemoteDataSource
import com.example.quote_domain.QuoteRepository
import com.example.quote_model.AuthorWithQuotes
import com.example.quote_model.QuoteResponse
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