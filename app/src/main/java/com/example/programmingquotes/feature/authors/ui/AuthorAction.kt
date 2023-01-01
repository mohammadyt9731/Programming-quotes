package com.example.programmingquotes.feature.authors.ui

sealed interface AuthorAction {
    object GetRandomQuote : AuthorAction
    object RefreshAuthors : AuthorAction
}
