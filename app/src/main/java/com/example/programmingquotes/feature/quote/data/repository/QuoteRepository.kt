package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun fetchAuthorQuotesFromApiAndInsertToDb(authorName: String): ResultWrapper<Unit>

    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotesView>
}