package com.example.programmingquotes.feature.quote.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)
}