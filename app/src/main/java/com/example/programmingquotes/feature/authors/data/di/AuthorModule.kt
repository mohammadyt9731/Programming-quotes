package com.example.programmingquotes.feature.authors.data.di

import com.example.programmingquotes.core.data.db.AppDatabase
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSourceImpl
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorModule {

    @Binds
    abstract fun provideAuthorLocalDataSource(authorLocalDataSourceImpl: AuthorLocalDataSourceImpl):
            AuthorLocalDataSource

    @Binds
    abstract fun provideAuthorRepository(AuthorRepositoryImpl: AuthorRepositoryImpl):
            AuthorRepository

    companion object {
        @Provides
        fun provideAuthorDao(appDatabase: AppDatabase) = appDatabase.authorDao()
    }
}