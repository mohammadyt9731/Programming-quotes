package com.example.programmingquotes.feature.quote.data.network.model

import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity

data class QuotesWithAuthorResponse(
    val name: String,
    val wikiUrl: String,
    val quoteCount: Int,
    val quotes: List<QuoteEntity>
)
