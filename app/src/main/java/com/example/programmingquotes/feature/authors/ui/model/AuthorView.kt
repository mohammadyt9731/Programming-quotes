package com.example.programmingquotes.feature.authors.ui.model

import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity

data class AuthorView(
    val name: String,
    val wikiUrl: String,
    val quoteCount: Int,
    val emoji: Int
)

fun AuthorEntity.toAuthorView() = AuthorView(
    name = name,
    wikiUrl = wikiUrl,
    quoteCount = quoteCount,
    emoji = emoji
)