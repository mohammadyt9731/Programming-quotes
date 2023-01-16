package com.example.programmingquotes.feature.authors.data.datasource.local

import com.example.author_data.db.dao.AuthorDao
import com.example.author_model.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.dao.QuoteDao
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthorLocalDataSourceImpl @Inject constructor(
    private val authorDao: AuthorDao,
    private val quoteDao: QuoteDao,
) :
    AuthorLocalDataSource {

    override suspend fun insertAuthors(authors: List<AuthorEntity>) =
        authorDao.insertAuthors(authors)

    override fun getAuthors(): Flow<List<AuthorEntity>> = authorDao.getAuthors()

    override fun getRandomQuote(): QuoteEntity? = quoteDao.getRandomQuote()
}