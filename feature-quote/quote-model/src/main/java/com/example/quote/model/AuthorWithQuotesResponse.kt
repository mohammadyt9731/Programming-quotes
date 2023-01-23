package com.example.quote.model


data class AuthorWithQuotesResponse(
    val name: String,
    val wikiUrl: String,
    val quotes: List<QuoteResponse>,
    val quoteCount: Int
) 
