package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.author.data.db.AuthorDataBase
import com.example.quote.data.db.QuoteDataBase
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
    internal abstract fun bindAuthorDataBase(appDatabase: AppDatabase): AuthorDataBase

    @Binds
    @Singleton
    internal abstract fun bindQuoteDataBase(appDatabase: AppDatabase): QuoteDataBase

    companion object {
        @Provides
        @Singleton
        internal fun provideRoom(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "programming_quotes"
            ).build()
        }
    }
}
