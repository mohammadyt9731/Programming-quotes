package com.example.author.data.di

import android.content.Context
import android.hardware.SensorManager
import com.example.author.data.datasource.local.AuthorLocalDataSource
import com.example.author.data.datasource.local.AuthorLocalDataSourceImpl
import com.example.author.data.datasource.remote.AuthorRemoteDataSource
import com.example.author.data.datasource.remote.AuthorRemoteDataSourceImpl
import com.example.author.data.db.AuthorDao
import com.example.author.data.db.AuthorDataBase
import com.example.author.data.network.AuthorApi
import com.example.author.data.repository.AuthorRepositoryImpl
import com.example.author.domain.AuthorRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthorModule {
    @Binds
    abstract fun bindAuthorLocalDataSource(impl: AuthorLocalDataSourceImpl): AuthorLocalDataSource
    @Binds
    abstract fun bindAuthorRepository(impl: AuthorRepositoryImpl): AuthorRepository
    @Binds
    abstract fun bindAuthorRemoteDataSource(impl: AuthorRemoteDataSourceImpl): AuthorRemoteDataSource
    companion object {
        @Provides
        fun provideAuthorDao(authorDataBase: AuthorDataBase): AuthorDao = authorDataBase.authorDao()
        @Provides
        fun provideAuthorApi(retrofit: Retrofit): AuthorApi = retrofit.create(AuthorApi::class.java)
        @Provides
        fun provideSensorManager(@ApplicationContext context: Context): SensorManager =
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}