package com.example.programmingquotes.feature.quote.ui.model

import com.example.programmingquotes.feature.authors.ui.model.AuthorView

internal data class AuthorWithQuotesView(
    val author: AuthorView,
    val quotes: List<QuoteView>
)

