package com.example.quote.data.datasource.local

import com.example.quote.data.db.QuoteDao
import com.example.quote.model.AuthorWithQuotes
import com.example.quote.model.QuoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class QuoteLocalDataSourceImpl @Inject constructor(
    private val quoteDao: QuoteDao
) : QuoteLocalDataSource {
    override suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>) =
        quoteDao.insertAuthorQuotes(quotes = quotes)

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes> =
        quoteDao.getAuthorWithQuotes(authorName)

    override suspend fun getRandomQuote(): QuoteEntity? = quoteDao.getRandomQuote()
}