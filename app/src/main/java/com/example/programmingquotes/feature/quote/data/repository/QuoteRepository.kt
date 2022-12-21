package com.example.programmingquotes.feature.quote.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import kotlinx.coroutines.flow.Flow

internal interface QuoteRepository {

    fun getAuthorWithQuotes(authorName: String,isRefresh: Boolean): Flow<ResultWrapper<AuthorWithQuotesView>>
}