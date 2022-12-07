package com.example.programmingquotes.feature.quote.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.ResultWrapper
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

 /*   init {
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
    }*/

    fun getQuotes(authorName: String) =
        viewModelScope.launch(Dispatchers.IO) {
            fetchAuthorQuotesFromApiAndInsertToDb(authorName)
            repository.getAuthorWithQuotes(authorName = authorName).collect { authorWithQuotes ->
                _authorWithQuotes.value = authorWithQuotes
            }
        }

    private fun fetchAuthorQuotesFromApiAndInsertToDb(authorName: String) {
        viewModelScope.launch {
            when (repository.fetchAuthorQuotesFromApiAndInsertToDb(authorName = authorName)) {
                is ResultWrapper.Success -> {
                    Log.i("Mohammad", "Success")
                }
                is ResultWrapper.ApplicationError -> {
                    Log.i("Mohammad", "app Error")
                }
                is ResultWrapper.HttpError -> {
                    Log.i("Mohammad", "http error")
                }
                is ResultWrapper.NetworkError -> {
                    Log.i("Mohammad", "network error")
                }
            }
        }
    }
}