package com.example.programmingquotes.feature.authors.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingquotes.feature.authors.data.repository.AuthorRepository
import com.example.programmingquotes.feature.authors.ui.model.AuthorView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(private val authorRepository: AuthorRepository) :
    ViewModel() {

    init {
        getAuthors()
    }

    fun insertAuthors(authors: List<AuthorView>) = viewModelScope.launch(Dispatchers.IO) {
        authorRepository.insertAuthors(authors)
    }

    fun getAuthors() = viewModelScope.launch(Dispatchers.IO) {
        authorRepository.getAuthors().collect { authors ->
            Log.i("Mohammad", authors.toString())
        }
    }
}