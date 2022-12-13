package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.data.db.entity.toQuoteEntity
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.model.toAuthorWithQuotesView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {

    override suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<Unit> {
        return safeApiCall {
            val response = remoteDataSource.fetchAuthorWithQuotes(authorName = authorName)

            response?.let {
                localDataSource.insertAuthorQuotes(
                    quotes = it.quotes.map { quoteResponse ->
                        quoteResponse.toQuoteEntity()
                    }
                )
            }
        }
    }

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotesView> =
        localDataSource.getAuthorWithQuotes(authorName).map { it.toAuthorWithQuotesView() }
}