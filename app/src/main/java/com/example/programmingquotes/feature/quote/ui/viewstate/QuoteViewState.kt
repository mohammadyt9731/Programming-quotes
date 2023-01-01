package com.example.programmingquotes.feature.quote.ui.viewstate

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView

internal data class QuoteViewState(
    val pageState: ResultWrapper<AuthorWithQuotesView> = ResultWrapper.UnInitialize
)
