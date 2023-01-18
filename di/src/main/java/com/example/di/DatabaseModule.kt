package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.author_data.db.AuthorDataBase
import com.example.quote_data.db.QuoteDataBase
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindAuthorDataBase(appDatabase: AppDatabase): AuthorDataBase

    @Binds
    @Singleton
    abstract fun bindQuoteDataBase(appDatabase: AppDatabase): QuoteDataBase

    companion object {
        @Provides
        @Singleton
        fun provideRoom(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "programming_quotes"
            ).build()
        }
    }
}
