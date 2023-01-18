package com.example.author_domain.usecase

import com.example.author_domain.AuthorRepository
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(private val repository: AuthorRepository) {
    operator fun invoke() = repository.getRandomQuote()
}