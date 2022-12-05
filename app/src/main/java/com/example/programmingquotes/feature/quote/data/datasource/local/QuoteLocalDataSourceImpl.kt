package com.example.programmingquotes.feature.quote.data.datasource.local

import com.example.programmingquotes.feature.quote.data.db.dao.QuoteDao
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteLocalDataSourceImpl @Inject constructor(
    private val quoteDao: QuoteDao
) : QuoteLocalDataSource {

    override suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAuthorQuotes(quotes = quotes)
    }

    override fun getAuthorQuotes(authorName: String): Flow<List<QuoteEntity>> {
        TODO()
    }
}