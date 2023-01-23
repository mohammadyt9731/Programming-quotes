package com.example.quote.domain.usecase

import com.example.quote.domain.QuoteRepository
import javax.inject.Inject

class GetAuthorWithQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    operator fun invoke(authorName: String) =
        repository.getAuthorWithQuotes(authorName)
}