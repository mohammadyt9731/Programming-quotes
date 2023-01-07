package com.example.programmingquotes.feature.authors.ui

sealed interface AuthorAction {
    object RefreshAuthors : AuthorAction
    object StartSensorManager : AuthorAction
    object StopSensorManager : AuthorAction
}
