package com.example.author.ui

internal sealed interface AuthorAction {
    object RefreshAuthors : AuthorAction
    object StartSensorManager : AuthorAction
    object StopSensorManager : AuthorAction
}
