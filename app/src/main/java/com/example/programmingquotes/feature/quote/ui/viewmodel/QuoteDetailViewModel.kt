package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.domain.QuoteRepository
import com.example.programmingquotes.feature.quote.domain.usecase.GetAuthorWithQuotesUseCase
import com.example.programmingquotes.feature.quote.ui.viewstate.QuoteDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteDetailViewModel @Inject constructor(
    private val getAuthorWithQuotesUseCase: GetAuthorWithQuotesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableStateFlow(QuoteDetailViewState())
    val viewState = _viewState.asStateFlow()

    val errorChannel = Channel<String>()

    init {
        val authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getQuotes(authorName)
    }

    private fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.emit(_viewState.value.copy(authorWithQuotes = ResultWrapper.Loading))
            getAuthorWithQuotesUseCase(authorName)
                .catch {
                    errorChannel.send(it.message.toString())
                    _viewState.emit(
                        _viewState.value.copy(
                            authorWithQuotes = ResultWrapper.Error(
                                Errors.App(msg = it.message.toString())
                            )
                        )
                    )
                }
                .collect { authorWithQuotes ->
                    _viewState.emit(
                        _viewState.value.copy(
                            authorWithQuotes = ResultWrapper.Success(authorWithQuotes)
                        )
                    )
                }
        }
}