package com.example.programmingquotes.feature.quote.data.network.model

import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.ui.model.QuoteView

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

    fun toQuoteView() = QuoteView(
        id = id,
        author = author,
        quote = en
    )
}
