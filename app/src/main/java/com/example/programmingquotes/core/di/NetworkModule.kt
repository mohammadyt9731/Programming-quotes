package com.example.programmingquotes.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.programmingquotes.core.data.network.NetworkConnectivity
import com.example.programmingquotes.core.data.network.NetworkConnectivityImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    companion object {
        @Provides
        fun provideLoggerInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        @Provides
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            timeOut: Long
        ): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .build()

        @Provides
        fun provideBaseUrl(): String = "http://167.235.142.70:5002"

        @Provides
        fun provideTimeOut(): Long = 3L

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides
        fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    @Binds
    abstract fun bindNetworkConnectivity(impl: NetworkConnectivityImpl): NetworkConnectivity
}
