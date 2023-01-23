package com.example.quote.data.di

import com.example.quote.data.datasource.local.QuoteLocalDataSource
import com.example.quote.data.datasource.local.QuoteLocalDataSourceImpl
import com.example.quote.data.datasource.remote.QuoteRemoteDataSource
import com.example.quote.data.datasource.remote.QuoteRemoteDataSourceImpl
import com.example.quote.data.db.QuoteDao
import com.example.quote.data.db.QuoteDataBase
import com.example.quote.data.network.QuoteApi
import com.example.quote.data.repository.QuoteRepositoryImpl
import com.example.quote.domain.QuoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class QuoteModule {
    companion object {
        @Provides
        fun provideQuoteApi(
            retrofit: Retrofit
        ): QuoteApi =
            retrofit.create(QuoteApi::class.java)
        @Provides
        fun provideQuoteDao(quoteDataBase: QuoteDataBase): QuoteDao =
            quoteDataBase.quoteDao()
    }
    @Binds
    abstract fun bindQuoteLocalDataSource(impl: QuoteLocalDataSourceImpl): QuoteLocalDataSource
    @Binds
    abstract fun bindQuoteRemoteDataSource(impl: QuoteRemoteDataSourceImpl): QuoteRemoteDataSource
    @Binds
    abstract fun bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository
}