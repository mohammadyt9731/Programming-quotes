package com.example.programmingquotes.feature.authors.data.db.entity

import androidx.room.*
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(authors: List<AuthorEntity>)

    @Query("SELECT * FROM author")
    fun getAuthors(): Flow<List<AuthorEntity>>

    @Transaction
    @Query("SELECT * FROM author WHERE name=:authorName")
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}