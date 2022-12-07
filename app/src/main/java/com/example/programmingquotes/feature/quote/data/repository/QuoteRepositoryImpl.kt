package com.example.programmingquotes.feature.quote.data.repository

import android.util.Log
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.data.db.entity.toQuoteEntity
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import com.example.programmingquotes.feature.quote.ui.model.toAuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.model.toQuoteView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {

    override suspend fun fetchAuthorQuotesFromApiAndInsertToDb(authorName: String): ResultWrapper<List<QuoteView>?> {
        return safeApiCall {
            val response = remoteDataSource.getAuthorWithQuotes(authorName = authorName)

            response?.let {
                localDataSource.insertAuthorQuotes(
                    quotes = it.quotes.map { quoteResponse ->
                        quoteResponse.toQuoteEntity()
                    })
            }
            listOf()
        }
    }

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotesView> =
        localDataSource.getAuthorWithQuotes(authorName).map { it.toAuthorWithQuotesView() }

    override suspend fun insertQuotes(quotes: List<QuoteView>) =
        localDataSource.insertAuthorQuotes(quotes = quotes.map { it.toQuoteEntity() })
}