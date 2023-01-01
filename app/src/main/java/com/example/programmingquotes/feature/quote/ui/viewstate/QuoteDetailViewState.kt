package com.example.programmingquotes.feature.quote.ui.viewstate

import com.example.programmingquotes.feature.authors.ui.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView

internal data class QuoteDetailViewState(
    val authorWithQuotes: AuthorWithQuotesView = AuthorWithQuotesView(
        AuthorView("", "", 0, 0),
        emptyList()
    )
)
