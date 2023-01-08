package com.example.programmingquotes.feature.quote.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse

@Entity(tableName = "quote")
internal data class QuoteEntity(
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
