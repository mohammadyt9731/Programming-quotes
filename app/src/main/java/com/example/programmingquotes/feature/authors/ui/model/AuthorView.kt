package com.example.programmingquotes.feature.authors.ui.model

internal data class AuthorView(
    val name: String,
    val wikiUrl: String,
    val quoteCount: Int,
    val emoji: Int
)