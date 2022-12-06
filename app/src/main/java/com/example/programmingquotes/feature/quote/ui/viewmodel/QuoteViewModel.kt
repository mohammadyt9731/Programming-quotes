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
class QuoteViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _authorWithQuotes = MutableStateFlow(
        AuthorWithQuotesView(
            AuthorView("", "", 0, 0),
            emptyList()
        )
    )
    val authorWithQuotes: StateFlow<AuthorWithQuotesView> = _authorWithQuotes

    init {
        var counter = 0
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertQuotes(
                listOf(
                    QuoteView(id = "${counter++}", "Mohammad yazdi", "AAAAA$counter"),
                    QuoteView(id = "${counter++}", "Mohammad yazdi", "AAAAA$counter"),
                    QuoteView(id = "${counter++}", "Mohammad yazdi", "AAAAA$counter"),
                    QuoteView(id = "${counter++}", "Mohammad yazdi3", "AAAAA$counter"),
                    QuoteView(id = "${counter++}", "Mohammad yazdi3", "AAAAA$counter"),
                    QuoteView(id = "${counter++}", "Mohammad yazdi4", "AAAAA$counter"),
                )
            )
        }
    }

    fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAuthorWithQuotes(authorName = authorName).collect { authorWithQuotes ->
                _authorWithQuotes.value = authorWithQuotes
            }
        }

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