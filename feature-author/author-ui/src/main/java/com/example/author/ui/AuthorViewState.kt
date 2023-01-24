package com.example.author.ui

import com.example.author.model.AuthorEntity
import com.example.base.ResultWrapper
import com.example.quote.model.QuoteResponse

internal data class AuthorViewState(
    internal val authors: ResultWrapper<List<AuthorEntity>> = ResultWrapper.UnInitialize,
    internal val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize,
    internal val bottomSheet: ResultWrapper<QuoteResponse?> = ResultWrapper.UnInitialize
)
