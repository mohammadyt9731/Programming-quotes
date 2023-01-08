package com.example.programmingquotes.feature.quote.ui.viewstate

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes

internal data class QuoteDetailViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize
)
