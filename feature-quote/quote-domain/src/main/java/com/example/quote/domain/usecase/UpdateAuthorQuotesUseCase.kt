package com.example.quote.domain.usecase

import com.example.quote.domain.QuoteRepository
import javax.inject.Inject

class UpdateAuthorQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(authorName: String) =
        repository.fetchAuthorQuotesAndInsertToDb(authorName)
}