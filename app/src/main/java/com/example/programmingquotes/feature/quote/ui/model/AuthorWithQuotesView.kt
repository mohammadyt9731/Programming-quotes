package com.example.programmingquotes.feature.quote.ui.model

import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.authors.ui.model.toAuthorView
import com.example.programmingquotes.feature.quote.data.db.entity.toQuoteEntity
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import com.example.programmingquotes.feature.quote.data.network.model.AuthorWithQuotesResponse

data class AuthorWithQuotesView(
    val author: AuthorView,
    val quotes: List<QuoteView>
)

fun AuthorWithQuotes.toAuthorWithQuotesView() = AuthorWithQuotesView(
    author = authorEntity.toAuthorView(),
    quotes = quotes.map { it.toQuoteView() }
)

fun AuthorWithQuotesResponse.toAuthorWithQuotesView() = AuthorWithQuotesView(
    author = AuthorView(name = name, wikiUrl = wikiUrl, quoteCount = quoteCount, emoji = 0),
    quotes = quotes.map { it.toQuoteEntity().toQuoteView() }
)
