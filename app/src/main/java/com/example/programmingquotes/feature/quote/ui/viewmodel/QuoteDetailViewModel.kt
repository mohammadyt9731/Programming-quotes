package com.example.programmingquotes.feature.quote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.ResultWrapper
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import com.example.programmingquotes.feature.quote.data.repository.QuoteRepository
import com.example.programmingquotes.feature.quote.ui.model.AuthorWithQuotesView
import com.example.programmingquotes.feature.quote.ui.model.QuoteView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _authorWithQuotes = MutableStateFlow(
        AuthorWithQuotesView(
            AuthorView("", "", 0, 0),
            emptyList()
        )
    )
    val authorWithQuotes: StateFlow<AuthorWithQuotesView> = _authorWithQuotes


    fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAuthorWithQuotes(authorName = authorName).collect { authorWithQuotes ->
                _authorWithQuotes.value = authorWithQuotes
            }
        }
}