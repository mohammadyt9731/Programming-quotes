package com.example.author_domain.usecase

import com.example.quote_domain.usecase.GetRandomQuoteUseCase
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(private val getRandomQuoteUseCase: GetRandomQuoteUseCase) {
    operator fun invoke() = getRandomQuoteUseCase.invoke()
}
