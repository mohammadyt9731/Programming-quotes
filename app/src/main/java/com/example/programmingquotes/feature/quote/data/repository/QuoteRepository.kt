package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun getAuthorQuotesFromApiAndInsertToDb(authorName: String): Flow<List<QuoteView>>

    fun getAuthorQuotes(authorName: String): Flow<List<QuoteView>>
}