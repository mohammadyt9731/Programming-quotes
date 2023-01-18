package com.example.quote_ui.action

sealed interface QuoteAction {
    object GetAuthorWithQuotesWhenRefresh : QuoteAction
}