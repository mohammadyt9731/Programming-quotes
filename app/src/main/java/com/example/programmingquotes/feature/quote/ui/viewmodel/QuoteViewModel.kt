package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _quotes = MutableStateFlow<List<QuoteView>>(emptyList())
    val quotes: StateFlow<List<QuoteView>> = _quotes

    fun quotes(authorName: String): Flow<List<QuoteView>> =
        repository.getAuthorQuotes(authorName = authorName)

    private fun getAuthorQuotesFromApiAndInsertToDb(authorName: String) {
        viewModelScope.launch {
            when (repository.getAuthorQuotesFromApiAndInsertToDb(authorName = authorName)) {
                is ResultWrapper.Success -> {}
                is ResultWrapper.ApplicationError -> {}
                is ResultWrapper.HttpError -> {}
                is ResultWrapper.NetworkError -> {}
            }
        }
    }
}