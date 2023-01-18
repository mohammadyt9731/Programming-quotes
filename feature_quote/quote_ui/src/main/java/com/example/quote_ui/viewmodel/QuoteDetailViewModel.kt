package com.example.quote_ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.common.Constants
import com.example.common_android.BaseViewModel
import com.example.quote_domain.usecase.GetAuthorWithQuotesUseCase
import com.example.quote_ui.viewstate.QuoteDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    private val getAuthorWithQuotesUseCase: GetAuthorWithQuotesUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<QuoteDetailViewState, Nothing>(QuoteDetailViewState()) {
    init {
        val authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getQuotes(authorName)
    }

    private fun getQuotes(authorName: String) = getAuthorWithQuotesUseCase(authorName)
        .execute {
            copy(authorWithQuotes = it)
        }
}