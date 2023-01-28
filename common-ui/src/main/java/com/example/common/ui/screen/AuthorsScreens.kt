package com.example.common.ui.screen

sealed class AuthorsScreens(val route: String)  {

    object AuthorsScreen : AuthorsScreens("author_screen")
}