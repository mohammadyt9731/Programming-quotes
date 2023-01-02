package com.example.programmingquotes.feature.quote.ui.action

sealed interface QuoteAction{
    object GetAuthorWithQuotesWhenRefresh : QuoteAction
}