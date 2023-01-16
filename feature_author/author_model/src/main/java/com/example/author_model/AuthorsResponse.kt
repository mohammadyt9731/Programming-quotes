package com.example.author_model

import com.example.common.generateRandomEmoji

data class AuthorResponse(
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
}