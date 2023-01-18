package com.example.author_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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


