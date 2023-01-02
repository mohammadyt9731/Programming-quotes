package com.example.programmingquotes.feature.quote.data.network.model

import com.example.programmingquotes.feature.authors.ui.AuthorView
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView

internal data class AuthorWithQuotesResponse(
    val name: String,
    val wikiUrl: String,
    val quotes: List<QuoteResponse>,
    val quoteCount: Int
) {
    fun toAuthorWithQuotesView() = AuthorWithQuotesView(
        author = AuthorView(
            name = name,
            wikiUrl = wikiUrl,
            quoteCount = quoteCount,
            emoji = 0
        ),
        quotes = quotes.map { it.toQuoteView() }
    )
}
