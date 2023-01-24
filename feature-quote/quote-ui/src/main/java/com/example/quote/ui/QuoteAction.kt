package com.example.quote.ui

sealed interface QuoteAction {
    object GetAuthorWithQuotesWhenRefresh : QuoteAction
}