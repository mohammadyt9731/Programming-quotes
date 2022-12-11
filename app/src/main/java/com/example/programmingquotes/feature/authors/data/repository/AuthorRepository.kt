package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {

    suspend fun insertAuthors(authors: List<AuthorView>)

    fun getRandomQuote(): Flow<QuoteView?>

    fun getAuthors(): Flow<List<AuthorView>>

    suspend fun getRandomQuoteFromApi(): ResultWrapper<QuoteView?>

    suspend fun getAuthorsFromApiAndInsertToDb(): ResultWrapper<Unit>
}