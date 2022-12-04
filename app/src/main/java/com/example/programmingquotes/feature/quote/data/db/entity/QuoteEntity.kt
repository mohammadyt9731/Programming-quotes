package com.example.programmingquotes.feature.quote.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.programmingquotes.feature.quote.ui.model.QuoteView

@Entity(tableName = "quote")
data class QuoteEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val en: String
)

fun QuoteEntity.toQuoteView() = QuoteView(
    id = id,
    author = author,
    en = en
)