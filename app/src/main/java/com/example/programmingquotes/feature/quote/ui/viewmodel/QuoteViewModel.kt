package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.NetworkConnectivity
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val networkConnectivity: NetworkConnectivity,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _authorWithQuotes = MutableStateFlow(
        AuthorWithQuotesView(
            AuthorView("", "", 0, 0),
            emptyList()
        )
    )
    val authorWithQuotes: StateFlow<AuthorWithQuotesView> = _authorWithQuotes

    private val _pageState = MutableStateFlow<ResultWrapper<Unit>>(ResultWrapper.Success(Unit))
    val pageState: StateFlow<ResultWrapper<Unit>> = _pageState

    init {
        val authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getQuotes(authorName)
    }

    private fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAuthorWithQuotes(authorName = authorName).collect { authorWithQuotes ->
                if (authorWithQuotes.quotes.isEmpty()) {
                    fetchAuthorQuotesFromApiAndInsertToDb(authorName)
                }
                _authorWithQuotes.emit(authorWithQuotes)
            }
        }

    private suspend fun fetchAuthorQuotesFromApiAndInsertToDb(authorName: String) {
        if (networkConnectivity.isNetworkConnected()) {
            _pageState.emit(ResultWrapper.Loading)
            val response =
                repository.fetchAuthorQuotesFromApiAndInsertToDb(authorName = authorName)
            _pageState.emit(response)
        } else {
            _pageState.emit(ResultWrapper.NetworkError)
        }
    }
}