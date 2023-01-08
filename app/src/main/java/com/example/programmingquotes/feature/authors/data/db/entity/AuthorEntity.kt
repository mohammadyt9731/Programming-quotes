package com.example.programmingquotes.feature.authors.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
internal data class AuthorEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "wiki_url")
    val wikiUrl: String,
    @ColumnInfo(name = "quote_count")
    val quoteCount: Int,
    val emoji: Int
)


