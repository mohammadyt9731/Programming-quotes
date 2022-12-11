package com.example.programmingquotes.feature.quote.ui.model

import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse

data class QuoteView(
    val id: String,
    val author: String,
    val quote: String
)

fun QuoteEntity.toQuoteView() = QuoteView(
    id = id,
    author = author,
    quote = quote
)

fun QuoteResponse.toQuoteView() = QuoteView(
    id = id,
    author = author,
    quote = en
)
