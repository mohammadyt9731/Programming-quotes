package com.example.programmingquotes.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorDao
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorEntity

@Database(entities = [AuthorEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
}