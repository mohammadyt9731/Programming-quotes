package com.example.programmingquotes.feature.authors.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.programmingquotes.feature.authors.ui.model.AuthorView

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

fun AuthorView.toAuthorEntity() = AuthorEntity(
    name = name,
    wikiUrl = wikiUrl,
    quoteCount = quoteCount,
    emoji = emoji
)
