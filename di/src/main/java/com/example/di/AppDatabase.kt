package com.example.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.author.data.db.AuthorDataBase
import com.example.author.model.AuthorEntity
import com.example.quote.data.db.QuoteDataBase
import com.example.quote.model.QuoteEntity

@Database(entities = [AuthorEntity::class, QuoteEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase(), AuthorDataBase, QuoteDataBase