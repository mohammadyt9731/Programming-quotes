package com.example.programmingquotes.feature.quote.data.datasource.local

import com.example.programmingquotes.feature.authors.data.db.dao.AuthorDao
import com.example.programmingquotes.feature.quote.data.db.dao.QuoteDao
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteLocalDataSourceImpl @Inject constructor(
    private val quoteDao: QuoteDao,private val authorDao: AuthorDao
) : QuoteLocalDataSource {

    override suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>) =
        quoteDao.insertAuthorQuotes(quotes = quotes)

    override fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes> =
        authorDao.getAuthorWithQuotes(authorName)
}