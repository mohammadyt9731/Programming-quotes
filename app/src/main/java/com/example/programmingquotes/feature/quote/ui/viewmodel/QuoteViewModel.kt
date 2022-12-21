package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.data.network.NetworkConnectivity
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val networkConnectivity: NetworkConnectivity,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var authorName: String
//    private val _authorWithQuotes = MutableStateFlow(
//        AuthorWithQuotesView(
//            AuthorView("", "", 0, 0),
//            emptyList()
//        )
//    )
//    val authorWithQuotes: StateFlow<AuthorWithQuotesView> = _authorWithQuotes

    private val _pageState =
        MutableStateFlow<ResultWrapper<AuthorWithQuotesView>>(ResultWrapper.UnInitialize)
    val pageState: StateFlow<ResultWrapper<AuthorWithQuotesView>> = _pageState

    init {
        authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getAuthorWithQuotes()
    }

     fun getAuthorWithQuotes(name: String = authorName,isRefresh: Boolean = false) =
        viewModelScope.launch(Dispatchers.IO) {
            _pageState.emit(ResultWrapper.Loading)
            repository.getAuthorWithQuotes(authorName = name, isRefresh = isRefresh)
                .collect { authorWithQuotes ->
                _pageState.emit(authorWithQuotes)
            }
        }

//    private fun getQuotes(name: String = authorName) =
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAuthorWithQuotes(authorName = name).collect { authorWithQuotes ->
//                if (authorWithQuotes.quotes.isEmpty()) {
//                    fetchAuthorQuotesAndInsertToDb(name)
//                }
//                _authorWithQuotes.emit(authorWithQuotes)
//            }
//        }
//
//    fun fetchAuthorQuotesAndInsertToDb(name: String = authorName) {
//        viewModelScope.launch {
//            if (networkConnectivity.isNetworkConnected()) {
//                _pageState.emit(ResultWrapper.Loading)
//                val response =
//                    repository.fetchAuthorQuotesAndInsertToDb(authorName = name)
//                _pageState.emit(response)
//            } else {
//                _pageState.emit(
//                    Errors.Network(msg = R.string.msg_no_internet.toString())
//                )
//            }
//        }
//    }
}