package com.example.quote_ui.viewstate

import com.example.common.ResultWrapper
import com.example.quote_model.AuthorWithQuotes
data class QuoteViewState(
    val authorWithQuotes: ResultWrapper<AuthorWithQuotes> = ResultWrapper.UnInitialize,
    val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize
)
