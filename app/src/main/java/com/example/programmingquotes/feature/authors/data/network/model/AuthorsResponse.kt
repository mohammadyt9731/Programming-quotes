package com.example.programmingquotes.feature.authors.data.network.model

import com.example.programmingquotes.core.common.generateRandomEmoji
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.authors.ui.AuthorView

internal data class AuthorResponse(
    val name: String,
    val quoteCount: Int,
    val wikiUrl: String
) {

    fun toAuthorEntity() = AuthorEntity(
        name = name,
        wikiUrl = wikiUrl,
        quoteCount = quoteCount,
        emoji = generateRandomEmoji()
    )

    fun toAuthorView() = AuthorView(
        name = name,
        wikiUrl = wikiUrl,
        quoteCount = quoteCount,
        emoji = generateRandomEmoji()
    )
}