package com.example.programmingquotes.feature.authors.ui

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.QuoteView

internal data class AuthorViewState(
    val authors: ResultWrapper<List<AuthorView>> = ResultWrapper.UnInitialize,
    val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize,
    val bottomSheet: ResultWrapper<QuoteView?> = ResultWrapper.UnInitialize
)
