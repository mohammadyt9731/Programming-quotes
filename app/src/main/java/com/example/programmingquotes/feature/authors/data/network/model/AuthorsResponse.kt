package com.example.programmingquotes.feature.authors.data.network.model


data class AuthorResponse(
    val name: String,
    val quoteCount: Int,
    val wikiUrl: String
)