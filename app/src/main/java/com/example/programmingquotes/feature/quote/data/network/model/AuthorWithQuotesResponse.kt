package com.example.programmingquotes.feature.quote.data.network.model

data class AuthorWithQuotesResponse(
    val name: String,
    val wikiUrl: String,
    val quoteCount: Int,
    val quotes: List<QuoteResponse>
)
