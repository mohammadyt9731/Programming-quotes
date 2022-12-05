package com.example.programmingquotes.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorDao
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.dao.QuoteDao
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity

@Database(entities = [AuthorEntity::class, QuoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
    abstract fun quoteDao(): QuoteDao
}