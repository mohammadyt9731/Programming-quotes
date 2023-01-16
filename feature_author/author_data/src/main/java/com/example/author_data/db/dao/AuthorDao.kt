package com.example.author_data.db.dao

import androidx.room.*
import com.example.author_model.AuthorEntity
import com.example.programmingquotes.feature.quote.data.db.relation.AuthorWithQuotes
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(authors: List<AuthorEntity>)

    @Query("SELECT * FROM author")
    fun getAuthors(): Flow<List<AuthorEntity>>

    @Transaction
    @Query("SELECT * FROM author WHERE name = (:authorName)")
    fun getAuthorWithQuotes(authorName: String): Flow<AuthorWithQuotes>
}