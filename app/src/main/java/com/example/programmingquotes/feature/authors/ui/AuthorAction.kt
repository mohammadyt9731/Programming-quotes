package com.example.programmingquotes.feature.authors.ui

sealed interface AuthorAction {
    object RefreshAuthors : AuthorAction
}
