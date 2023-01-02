package com.example.programmingquotes.feature.quote.data.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView

internal data class AuthorWithQuotes(
    @Embedded val authorEntity: AuthorEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "author"
    )
    val quotes: List<QuoteEntity>
) {

    fun toAuthorWithQuotesView() = AuthorWithQuotesView(
        author = authorEntity.toAuthorView(),
        quotes = quotes.map { it.toQuoteView() }
    )
}
