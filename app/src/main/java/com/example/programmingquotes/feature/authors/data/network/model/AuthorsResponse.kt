package com.example.programmingquotes.feature.authors.data.network.model


import com.google.gson.annotations.SerializedName

data class AuthorsResponse(
    @SerializedName("Edsger W. Dijkstra")
    val authorResponse: AuthorResponse,

) {
    data class AuthorResponse(
        val name: String,
        val quoteCount: Int,
        val wikiUrl: String
    )
}