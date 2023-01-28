package com.example.quote.model

data class QuoteResponse(
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
