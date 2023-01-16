package com.example.author_domain

import com.example.author_model.AuthorEntity
import com.example.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import kotlinx.coroutines.flow.Flow

internal interface AuthorRepository {
    fun getRandomQuote(): Flow<ResultWrapper<QuoteResponse>>
    fun getAuthors(): Flow<List<AuthorEntity>>
    suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit>
}