package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow
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
}