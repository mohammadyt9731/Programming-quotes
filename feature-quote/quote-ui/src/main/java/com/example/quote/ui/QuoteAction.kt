package com.example.quote.ui

internal sealed interface QuoteAction {
    object GetAuthorWithQuotesWhenRefresh : QuoteAction
}