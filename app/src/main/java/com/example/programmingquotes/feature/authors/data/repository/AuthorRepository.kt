package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {

    suspend fun insertAuthors(authors: List<AuthorView>)

    fun getAuthors(): Flow<List<AuthorView>>
}