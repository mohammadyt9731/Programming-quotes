package com.example.quote_data.db

import androidx.room.*
import com.example.quote_model.AuthorWithQuotes
import com.example.quote_model.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)

    @Query("SELECT * FROM quote ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote(): QuoteEntity?

    @Transaction
    @Query("SELECT * FROM author WHERE name = (:authorName)")
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}