package com.example.author.model

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

    private fun generateRandomEmoji() = (128512..128580).random()
}