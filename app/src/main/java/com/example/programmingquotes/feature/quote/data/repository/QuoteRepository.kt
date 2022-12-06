package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun getAuthorQuotesFromApiAndInsertToDb(authorName: String): ResultWrapper<List<QuoteView>?>

    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotesView>

    suspend fun insertQuotes(quotes: List<QuoteView>)
}