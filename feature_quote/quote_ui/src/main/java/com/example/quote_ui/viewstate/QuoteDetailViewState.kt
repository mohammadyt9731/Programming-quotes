package com.example.quote_ui.viewstate

import com.example.common.ResultWrapper
import com.example.quote_model.AuthorWithQuotes

internal data class QuoteDetailViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize
)
