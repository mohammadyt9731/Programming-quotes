package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var authorName: String
    private val _pageState =
        MutableStateFlow<ResultWrapper<AuthorWithQuotesView>>(ResultWrapper.UnInitialize)
    val pageState: StateFlow<ResultWrapper<AuthorWithQuotesView>> = _pageState

    val errorChannel = Channel<String>()

    init {
        authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getAuthorWithQuotes()
    }

    fun getAuthorWithQuotes(name: String = authorName, isRefresh: Boolean = false) =
        viewModelScope.launch(Dispatchers.IO) {
            _pageState.emit(ResultWrapper.Loading)
            repository.getAuthorWithQuotes(authorName = name, isRefresh = isRefresh)
                .collect {
                    if (it is ResultWrapper.Error) {
                        errorChannel.send(it.errors.message)
                    } else {
                        _pageState.emit(it)
                    }
                }
        }
}