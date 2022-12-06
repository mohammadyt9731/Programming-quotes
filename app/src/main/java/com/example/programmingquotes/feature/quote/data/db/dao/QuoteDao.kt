package com.example.programmingquotes.feature.quote.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.programmingquotes.feature.quote.data.db.entity.QuoteEntity
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow


@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthorQuotes(quotes: List<QuoteEntity>)

    @Transaction
    @Query("SELECT * FROM author WHERE name=:authorName")
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}