package com.example.author_ui

sealed interface AuthorAction {
    object RefreshAuthors : AuthorAction
    object StartSensorManager : AuthorAction
    object StopSensorManager : AuthorAction
}
