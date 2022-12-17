package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.Constants
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QuoteDetailViewModel @Inject constructor(
    private val repository: QuoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _authorWithQuotes = MutableStateFlow(
        AuthorWithQuotesView(
            AuthorView("", "", 0, 0),
            emptyList()
        )
    )
    val authorWithQuotes: StateFlow<AuthorWithQuotesView> = _authorWithQuotes

    init {
        val authorName = savedStateHandle.get<String>(Constants.AUTHOR_NAME_KEY) ?: ""
        getQuotes(authorName)
    }

    private fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAuthorWithQuotes(authorName = authorName).collect { authorWithQuotes ->
                _authorWithQuotes.emit(authorWithQuotes)
            }
        }
}