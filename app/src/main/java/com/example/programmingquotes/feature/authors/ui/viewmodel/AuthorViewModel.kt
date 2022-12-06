package com.example.programmingquotes.feature.authors.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.core.common.generateRandomEmoji
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AuthorViewModel @Inject constructor(private val authorRepository: AuthorRepository) :
    ViewModel() {

    private val _authors = MutableStateFlow<List<AuthorView>>(emptyList())
    val authors: StateFlow<List<AuthorView>> = _authors

    private val _randomQuote = MutableStateFlow<AuthorView>(AuthorView("", "", 0, 0))
    val randomQuote: StateFlow<AuthorView> = _randomQuote

    init {
        getAuthors()

        viewModelScope.launch {

            authorRepository.insertAuthors(
                listOf(
                    AuthorView(
                        name = "Mohammad yazdi",
                        wikiUrl = "https://en.wikipedia.org/wiki/Edsger W. Dijkstra",
                        quoteCount = 12,
                        emoji = generateRandomEmoji()
                    ),
                    AuthorView(
                        name = "Mohammad yazdi1",
                        wikiUrl = "",
                        quoteCount = 22,
                        emoji = generateRandomEmoji()
                    ),
                    AuthorView(
                        name = "Mohammad yazdi2",
                        wikiUrl = "",
                        quoteCount = 33,
                        emoji = generateRandomEmoji()
                    ),
                    AuthorView(
                        name = "Mohammad yazdi3",
                        wikiUrl = "",
                        quoteCount = 44,
                        emoji = generateRandomEmoji()
                    ),
                    AuthorView(
                        name = "Mohammad yazdi4",
                        wikiUrl = "",
                        quoteCount = 55,
                        emoji = generateRandomEmoji()
                    ),
                    AuthorView(
                        name = "Mohammad yazdi5",
                        wikiUrl = "",
                        quoteCount = 11,
                        emoji = generateRandomEmoji()
                    ),
                )
            )
        }

    }

    private fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        authorRepository.getAuthors().collect {
            _authors.value = it
        }
    }
    fun getRandomAuthor() = viewModelScope.launch(Dispatchers.IO) {
        _randomQuote.value = AuthorView(name = "Ali${Random.nextInt(100)}", wikiUrl = "", 99, generateRandomEmoji())
    }
}