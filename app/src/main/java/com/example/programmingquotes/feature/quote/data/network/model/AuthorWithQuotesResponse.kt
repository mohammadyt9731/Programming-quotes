package com.example.programmingquotes.feature.quote.data.network.model

internal data class AuthorWithQuotesResponse(
    val name: String,
    val wikiUrl: String,
    val quotes: List<QuoteResponse>,
    val quoteCount: Int
)
