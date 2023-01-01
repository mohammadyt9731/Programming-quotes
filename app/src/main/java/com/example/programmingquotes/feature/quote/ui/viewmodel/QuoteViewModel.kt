package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.action.QuoteAction
import com.example.programmingquotes.feature.quote.ui.viewstate.QuoteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var authorName: String

    private val _viewState = MutableStateFlow(QuoteViewState())
    val viewState = _viewState.asStateFlow()

    val errorChannel = Channel<String>()

    init {
        authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getAuthorWithQuotes()
    }

    fun handleAction(action: QuoteAction) {
        when (action) {
            is QuoteAction.GetAuthorWithQuotesWhenRefresh -> getAuthorWithQuotes(isRefresh = true)
        }
    }

    fun getAuthorWithQuotes(name: String = authorName, isRefresh: Boolean = false) =
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(_viewState.value.copy(pageState = ResultWrapper.Loading))
            repository.getAuthorWithQuotes(authorName = name, isRefresh = isRefresh)
                .collect {
                    if (it is ResultWrapper.Error) {
                        errorChannel.send(it.errors.message)
                    }
                    _viewState.emit(_viewState.value.copy(pageState = it))
                }
        }
}