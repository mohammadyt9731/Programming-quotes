package com.example.quote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class QuoteEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val quote: String
) {
    fun toQuoteResponse() = QuoteResponse(
        id = id,
        author = author,
        en = quote
    )
}
