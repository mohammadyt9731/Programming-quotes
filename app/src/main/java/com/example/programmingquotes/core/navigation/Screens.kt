package com.example.programmingquotes.core.navigation

sealed class Screens(val route: String) {
    object SplashScreen : Screens("splash_screen")
    object AuthorsScreen : Screens("author_screen")
    object QuotesScreen : Screens("quotes_screen")
    object QuoteDetailScreen : Screens("quote_detail_screen")

    fun withArg(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
