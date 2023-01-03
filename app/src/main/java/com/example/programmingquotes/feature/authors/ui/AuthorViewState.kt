package com.example.programmingquotes.feature.authors.ui

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.ui.model.QuoteView

internal data class AuthorViewState(
    val authorsState: ResultWrapper<List<AuthorView>> = ResultWrapper.UnInitialize,
    val updateState: ResultWrapper<Unit> = ResultWrapper.UnInitialize,
    val bottomSheetState: ResultWrapper<QuoteView?> = ResultWrapper.UnInitialize
)
