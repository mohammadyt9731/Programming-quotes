package com.example.quote.ui.action

sealed interface QuoteAction {
    object GetAuthorWithQuotesWhenRefresh : QuoteAction
}