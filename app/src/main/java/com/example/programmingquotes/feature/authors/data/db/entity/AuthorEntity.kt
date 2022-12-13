package com.example.programmingquotes.feature.authors.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.programmingquotes.core.common.generateRandomEmoji
import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse

@Entity(tableName = "author")
data class AuthorEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String,
    @ColumnInfo(name = "quote_count")
    val quoteCount: Int,
    val emoji: Int
)

fun AuthorResponse.toAuthorEntity() = AuthorEntity(
    name = name,
    wikiUrl = wikiUrl,
    quoteCount = quoteCount,
    emoji = generateRandomEmoji()
)

