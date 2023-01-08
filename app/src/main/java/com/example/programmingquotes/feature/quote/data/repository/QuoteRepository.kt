package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow

internal interface QuoteRepository {

    suspend fun fetchAuthorQuotesAndInsertToDb(authorName: String): ResultWrapper<Unit>

    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}