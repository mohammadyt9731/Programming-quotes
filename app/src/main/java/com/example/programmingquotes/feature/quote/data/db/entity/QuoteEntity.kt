package com.example.programmingquotes.feature.quote.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.programmingquotes.feature.quote.data.network.model.QuoteResponse
import com.example.programmingquotes.feature.quote.ui.model.QuoteView

@Entity(tableName = "quote")
data class QuoteEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val quote: String
)

fun QuoteResponse.toQuoteEntity() = QuoteEntity(
    id = id,
    author = author,
    quote = en
)

fun QuoteView.toQuoteEntity() = QuoteEntity(
    id = id,
    author = author,
    quote = quote
)