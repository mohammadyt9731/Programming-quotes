package com.example.programmingquotes.feature.quote.data.network.model

import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity

internal data class QuoteResponse(
    val id: String,
    val author: String,
    val en: String
) {

    fun toQuoteEntity() = QuoteEntity(
        id = id,
        author = author,
        quote = en
    )
}
