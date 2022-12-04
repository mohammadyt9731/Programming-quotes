package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.db.entity.toAuthorEntity
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.authors.ui.model.toAuthorView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(private val authorLocalDataSource: AuthorLocalDataSource) :
    AuthorRepository {
    override suspend fun insertAuthors(authors: List<AuthorView>) =
        authorLocalDataSource.insertAuthors(authors.map { it.toAuthorEntity() })

    override fun getAuthors(): Flow<List<AuthorView>> =
        authorLocalDataSource.getAuthors()
            .map { it.map { authorEntity -> authorEntity.toAuthorView() } }
}