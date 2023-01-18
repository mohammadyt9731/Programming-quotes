package com.example.author_ui

import com.example.author_model.AuthorEntity
import com.example.common.ResultWrapper
import com.example.quote_model.QuoteResponse

data class AuthorViewState(
    val authors: ResultWrapper<List<AuthorEntity>> = ResultWrapper.UnInitialize,
    val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize,
    val bottomSheet: ResultWrapper<QuoteResponse?> = ResultWrapper.UnInitialize
)
