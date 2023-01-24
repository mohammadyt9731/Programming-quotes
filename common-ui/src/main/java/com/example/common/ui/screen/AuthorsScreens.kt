package com.example.common.ui.screen

sealed class AuthorsScreens(route: String) : BaseScreen(route) {

    object AuthorsScreen : AuthorsScreens("author_screen")
}