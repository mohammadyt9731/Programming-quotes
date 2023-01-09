package com.example.programmingquotes.feature.quote.domain.usecase

import com.example.programmingquotes.feature.quote.domain.QuoteRepository
import javax.inject.Inject

internal class UpdateAuthorQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke(authorName: String) =
        repository.fetchAuthorQuotesAndInsertToDb(authorName)
}