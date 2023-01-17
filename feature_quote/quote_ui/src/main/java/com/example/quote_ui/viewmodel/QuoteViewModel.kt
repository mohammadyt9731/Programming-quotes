package com.example.quote_ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.common.Constants
import com.example.common_android.BaseViewModel
import com.example.quote_domain.usecase.GetAuthorWithQuotesUseCase
import com.example.quote_domain.usecase.UpdateAuthorQuotesUseCase
import com.example.quote_ui.action.QuoteAction
import com.example.quote_ui.viewstate.QuoteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
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

        onEachAction { action ->
            when (action) {
                is QuoteAction.GetAuthorWithQuotesWhenRefresh -> fetchAuthorWithQuotes()
            }
        }
    }

    fun getAuthorWithQuotes(name: String = authorName) = getAuthorWithQuotesUseCase(name)
        .onEach {
            if (it.quotes.isEmpty()) {
                fetchAuthorWithQuotes()
            }
        }
        .execute {
            copy(authorWithQuotes = it)
        }

    private fun fetchAuthorWithQuotes(name: String = authorName) = suspend {
        updateAuthorQuotesUseCase(name)
    }.execute {
        copy(update = it)
    }
}