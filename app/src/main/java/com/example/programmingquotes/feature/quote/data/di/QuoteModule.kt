package com.example.programmingquotes.feature.quote.data.di

import com.example.programmingquotes.core.data.db.AppDatabase
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSource
import com.example.programmingquotes.feature.quote.data.datasource.local.QuoteLocalDataSourceImpl
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.programmingquotes.feature.quote.data.datasource.remote.QuoteRemoteDataSourceImpl
import com.example.programmingquotes.feature.quote.data.db.dao.QuoteDao
import com.example.programmingquotes.feature.quote.data.network.QuoteApi
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class QuoteModule {

    companion object {
        @Provides
        fun provideQuoteApi(
            retrofit: Retrofit
        ): QuoteApi =
            retrofit.create(QuoteApi::class.java)

        @Provides
        fun provideQuoteDao(appDatabase: AppDatabase): QuoteDao =
            appDatabase.quoteDao()
    }

    @Binds
    abstract fun bindQuoteLocalDataSource(impl: QuoteLocalDataSourceImpl): QuoteLocalDataSource

    @Binds
    abstract fun bindQuoteRemoteDataSource(impl: QuoteRemoteDataSourceImpl): QuoteRemoteDataSource

    @Binds
    abstract fun bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository
}