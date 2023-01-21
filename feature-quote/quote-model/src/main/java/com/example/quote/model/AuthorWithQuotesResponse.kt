package com.example.quote_model


data class AuthorWithQuotesResponse(
    val name: String,
    val wikiUrl: String,
    val quotes: List<QuoteResponse>,
    val quoteCount: Int
) 
