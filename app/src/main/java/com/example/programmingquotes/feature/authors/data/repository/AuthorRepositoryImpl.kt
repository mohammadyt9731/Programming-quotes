package com.example.programmingquotes.feature.authors.data.repository

import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.IsNetwork
import com.example.programmingquotes.core.data.network.safeApiCall
import com.example.programmingquotes.feature.authors.data.datasource.local.AuthorLocalDataSource
import com.example.programmingquotes.feature.authors.data.datasource.remote.AuthorRemoteDataSource
import com.example.programmingquotes.feature.authors.data.db.entity.toAuthorEntity
import com.example.programmingquotes.feature.authors.data.network.model.AuthorResponse
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.authors.ui.model.toAuthorView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import com.example.programmingquotes.feature.quote.ui.model.toQuoteView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val localDataSource: AuthorLocalDataSource,
    private val remoteDataSource: AuthorRemoteDataSource,
    private val isNetwork: IsNetwork
) :
    AuthorRepository {

    override suspend fun insertAuthors(authors: List<AuthorView>) =
        localDataSource.insertAuthors(authors.map { it.toAuthorEntity() })

    override fun getRandomQuote(): Flow<QuoteView> =
        localDataSource.getRandomQuote().map { it.toQuoteView() }

    override fun getAuthors(): Flow<List<AuthorView>> =
        localDataSource.getAuthors()
            .map { it.map { authorEntity -> authorEntity.toAuthorView() } }

    override suspend fun getRandomQuoteFromApi(): ResultWrapper<QuoteView?> {
        return if (isNetwork.getNetworkConnection()) {
            safeApiCall {
                remoteDataSource.getRandomQuote()?.toQuoteView()
            }
        } else {
            ResultWrapper.NetworkError()
        }
    }

    override suspend fun getAuthorsFromApiAndInsertToDb(): ResultWrapper<Map<String, AuthorResponse>?> {
        return if (isNetwork.getNetworkConnection()) {
            safeApiCall {
                val response = remoteDataSource.getAuthors()

                response?.let {
                    localDataSource.insertAuthors(
                        it.values.toList().map { authorResponse ->
                            authorResponse.toAuthorEntity()
                        })

                    it
                }
            }
        } else {
            ResultWrapper.NetworkError()
        }
    }
}