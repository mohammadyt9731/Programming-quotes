package com.example.programmingquotes.feature.authors.domain

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import kotlinx.coroutines.flow.Flow

internal interface AuthorRepository {

    fun getRandomQuote(): Flow<ResultWrapper<QuoteResponse>>

    fun getAuthors(): Flow<List<AuthorEntity>>

    suspend fun fetchAuthorsAndInsertToDb(): ResultWrapper<Unit>
}