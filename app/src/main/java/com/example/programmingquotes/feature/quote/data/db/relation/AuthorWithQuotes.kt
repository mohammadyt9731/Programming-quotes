package com.example.programmingquotes.feature.quote.data.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity

internal data class AuthorWithQuotes(
    @Embedded val authorEntity: AuthorEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "author"
    )
    val quotes: List<QuoteEntity>
)
