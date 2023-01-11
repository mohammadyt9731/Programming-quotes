package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.core.ui.BaseViewModel
import com.example.programmingquotes.feature.quote.domain.usecase.GetAuthorWithQuotesUseCase
import com.example.programmingquotes.feature.quote.ui.viewstate.QuoteDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
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