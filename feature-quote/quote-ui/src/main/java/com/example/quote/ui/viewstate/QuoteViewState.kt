package com.example.quote.ui.viewstate

import com.example.base.ResultWrapper
import com.example.quote.model.AuthorWithQuotes

data class QuoteViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize,
    val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize
)
