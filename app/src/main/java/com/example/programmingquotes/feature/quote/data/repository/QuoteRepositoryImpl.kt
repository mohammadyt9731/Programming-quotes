package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.data.db.entity.toQuoteView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val localDataSource: QuoteLocalDataSource,
    private val remoteDataSource: QuoteRemoteDataSource
) : QuoteRepository {

    override fun getAuthorQuotesFromApiAndInsertToDb(authorName: String): Flow<List<QuoteView>> {
        TODO("Not yet implemented")
    }

    override fun getAuthorQuotes(authorName: String): Flow<List<QuoteView>> {
        return localDataSource.getAuthorQuotes(authorName = authorName).map {
            it.map { quote ->
                quote.toQuoteView()
            }
        }
    }

}