package com.example.common.ui.screen

sealed class QuotesScreens(val route: String) {

    object QuotesScreen : QuotesScreens("quotes_screen") {
        fun createRoute(authorNameKey: String) = "$route/$authorNameKey"
    }

    object QuoteDetailScreen : QuotesScreens("quote_detail_screen") {
        fun createRoute(quoteIndexKey: Int, authorNameKey: String) =
            "$route/$quoteIndexKey/$authorNameKey"
    }
}