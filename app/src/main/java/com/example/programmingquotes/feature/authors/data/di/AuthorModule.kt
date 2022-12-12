package com.example.programmingquotes.feature.authors.data.di

import com.example.programmingquotes.core.data.db.AppDatabase
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSourceImpl
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSource
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSourceImpl
import com.example.programmingquotes.feature.authors.data.network.api.AuthorApi
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthorModule {

    @Binds
    abstract fun bindAuthorLocalDataSource(impl: AuthorLocalDataSourceImpl): AuthorLocalDataSource

    @Binds
    abstract fun bindAuthorRepository(impl: AuthorRepositoryImpl): AuthorRepository

    @Binds
    abstract fun bindAuthorRemoteDataSource(impl: AuthorRemoteDataSourceImpl): AuthorRemoteDataSource

    companion object {
        @Provides
        fun provideAuthorDao(appDatabase: AppDatabase) = appDatabase.authorDao()

        @Provides
        fun provideAuthorApi(retrofit: Retrofit): AuthorApi = retrofit.create(AuthorApi::class.java)
    }
}