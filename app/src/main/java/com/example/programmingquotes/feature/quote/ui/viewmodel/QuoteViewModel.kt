package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.ui.BaseViewModel
import com.example.programmingquotes.feature.quote.domain.usecase.GetAuthorWithQuotesUseCase
import com.example.programmingquotes.feature.quote.domain.usecase.UpdateAuthorQuotesUseCase
import com.example.programmingquotes.feature.quote.ui.action.QuoteAction
import com.example.programmingquotes.feature.quote.ui.viewstate.QuoteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteViewModel @Inject constructor(
    private val getAuthorWithQuotesUseCase: GetAuthorWithQuotesUseCase,
    private val updateAuthorQuotesUseCase: UpdateAuthorQuotesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<QuoteViewState, QuoteAction>(QuoteViewState()) {

    private var authorName: String

    init {
        authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getAuthorWithQuotes()
    }

    fun handleAction(action: QuoteAction) {
        when (action) {
            is QuoteAction.GetAuthorWithQuotesWhenRefresh -> fetchAuthorWithQuotes()
        }
    }

    fun getAuthorWithQuotes(name: String = authorName) = viewModelScope.launch(Dispatchers.IO) {
        setState { copy(authorWithQuotes = ResultWrapper.Loading) }
        getAuthorWithQuotesUseCase(name)
            .catch {
                setError(it.message.toString())
                setState {
                    copy(
                        authorWithQuotes = ResultWrapper.Error(
                            Errors.App(msg = it.message.toString())
                        )
                    )
                }
            }
            .collect {
                if (it.quotes.isEmpty()) {
                    fetchAuthorWithQuotes()
                }
                setState { copy(authorWithQuotes = ResultWrapper.Success(it)) }
            }
    }

    private fun fetchAuthorWithQuotes(name: String = authorName) =
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(update = ResultWrapper.Loading) }
            val response = updateAuthorQuotesUseCase(name)

            setState { copy(update = response) }

            if (response is ResultWrapper.Error) {
                setError(response.errors.message)
            }
        }
}