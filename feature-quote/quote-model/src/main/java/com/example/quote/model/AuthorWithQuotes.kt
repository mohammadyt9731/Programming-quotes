package com.example.quote_model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.author.model.AuthorEntity

data class AuthorWithQuotes(
    @Embedded val authorEntity: AuthorEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "author"
    )
    val quotes: List<QuoteEntity>
)
