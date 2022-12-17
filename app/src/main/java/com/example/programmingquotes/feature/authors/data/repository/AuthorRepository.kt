package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow

internal interface AuthorRepository {

    fun getRandomQuote(): Flow<QuoteView?>

    fun getAuthors(): Flow<List<AuthorView>>

    suspend fun fetchRandomQuote(): ResultWrapper<QuoteView>

    suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit>
}