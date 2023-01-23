package com.example.quote.ui.viewstate

import com.example.base.ResultWrapper
import com.example.quote.model.AuthorWithQuotes

data class QuoteDetailViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize
)
