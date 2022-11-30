package com.example.programmingquotes.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.programmingquotes.feature.authors.data.db.entity.AuthorsEntity

@Database(entities = [AuthorsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
}