package com.example.author_ui

import com.example.common.ResultWrapper
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse

internal data class AuthorViewState(
    val authors: ResultWrapper<List<AuthorEntity>> = ResultWrapper.UnInitialize,
    val update: ResultWrapper<Unit> = ResultWrapper.UnInitialize,
    val bottomSheet: ResultWrapper<QuoteResponse?> = ResultWrapper.UnInitialize
)
