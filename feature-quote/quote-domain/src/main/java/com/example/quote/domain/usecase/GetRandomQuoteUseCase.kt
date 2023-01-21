package com.example.quote_domain.usecase

import com.example.quote_domain.QuoteRepository
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    operator fun invoke() = repository.getRandomQuote()
}