package com.example.quote_domain.usecase

import com.example.quote_domain.QuoteRepository
import javax.inject.Inject

class GetAuthorWithQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    operator fun invoke(authorName: String) =
        repository.getAuthorWithQuotes(authorName)
}