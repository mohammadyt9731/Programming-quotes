package com.example.author.ui

sealed interface AuthorAction {
    object RefreshAuthors : AuthorAction
    object StartSensorManager : AuthorAction
    object StopSensorManager : AuthorAction
}
