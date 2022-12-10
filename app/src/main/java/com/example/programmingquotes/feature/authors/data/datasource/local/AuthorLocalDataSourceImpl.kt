package com.example.programmingquotes.feature.authors.data.datasource.local

import com.example.programmingquotes.feature.authors.data.db.dao.AuthorDao
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorLocalDataSourceImpl @Inject constructor(private val authorDao: AuthorDao) :
    AuthorLocalDataSource {
    override suspend fun insertAuthors(authors: List<AuthorEntity>) =
        authorDao.insertAuthors(authors)

    override fun getAuthors(): Flow<List<AuthorEntity>> =
        authorDao.getAuthors()
}