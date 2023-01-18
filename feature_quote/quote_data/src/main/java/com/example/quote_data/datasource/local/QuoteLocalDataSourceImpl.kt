package com.example.quote_data.datasource.local

import com.example.quote_data.db.QuoteDao
import com.example.quote_model.AuthorWithQuotes
import com.example.quote_model.QuoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class QuoteLocalDataSourceImpl @Inject constructor(
    private val quoteDao: QuoteDao
) : QuoteLocalDataSource {

    override suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>) =
        quoteDao.insertAuthorQuotes(quotes = quotes)

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes> =
        quoteDao.getAuthorWithQuotes(authorName)

    override fun getRandomQuote(): QuoteEntity? = quoteDao.getRandomQuote()
}