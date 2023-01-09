package com.example.programmingquotes.feature.quote.domain.usecase

import com.example.programmingquotes.feature.quote.domain.QuoteRepository
import javax.inject.Inject

internal class GetAuthorWithQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    operator fun invoke(authorName: String) =
        repository.getAuthorWithQuotes(authorName)
}