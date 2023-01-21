package com.example.author.data.datasource.local

import com.example.author.data.db.AuthorDao
import com.example.author.model.AuthorEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthorLocalDataSourceImpl @Inject constructor(
    private val authorDao: AuthorDao
) :
    AuthorLocalDataSource {
    override suspend fun insertAuthors(authors: List<AuthorEntity>) =
        authorDao.insertAuthors(authors)
    override fun getAuthors(): Flow<List<AuthorEntity>> = authorDao.getAuthors()
}