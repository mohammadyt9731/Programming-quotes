package com.example.author.data.db

import androidx.room.*
import com.example.author.model.AuthorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(authors: List<AuthorEntity>)
    @Query("SELECT * FROM author")
    fun getAuthors(): Flow<List<AuthorEntity>>
}