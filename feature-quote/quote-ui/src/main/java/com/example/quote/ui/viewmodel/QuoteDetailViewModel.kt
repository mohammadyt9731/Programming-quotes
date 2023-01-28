package com.example.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.base.Constants
import com.example.common.android.BaseViewModel
import com.example.quote.domain.usecase.GetAuthorWithQuotesUseCase
import com.example.quote.ui.viewstate.QuoteDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class QuoteDetailViewModel @Inject constructor(
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