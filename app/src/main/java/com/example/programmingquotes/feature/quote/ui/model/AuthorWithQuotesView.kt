package com.example.programmingquotes.feature.quote.ui.model

import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.authors.ui.model.toAuthorView
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes

data class AuthorWithQuotesView(
    val author: AuthorView,
    val quotes: List<QuoteView>
)

fun AuthorWithQuotes.toAuthorWithQuotesView() = AuthorWithQuotesView(
    author = authorEntity.toAuthorView(),
    quotes = quotes.map { it.toQuoteView() }
)
