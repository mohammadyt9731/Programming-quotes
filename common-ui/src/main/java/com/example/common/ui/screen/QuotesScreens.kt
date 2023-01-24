package com.example.common.ui.screen

sealed class QuotesScreens(route: String) : BaseScreen(route) {

    object QuotesScreen : QuotesScreens("quotes_screen")
    object QuoteDetailScreen : QuotesScreens("quote_detail_screen")
}