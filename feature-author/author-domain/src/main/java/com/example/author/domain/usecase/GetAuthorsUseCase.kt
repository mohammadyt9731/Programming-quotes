package com.example.author.domain.usecase

import com.example.author.domain.AuthorRepository
import javax.inject.Inject

class GetAuthorsUseCase @Inject constructor(private val repository: AuthorRepository) {
    operator fun invoke() = repository.getAuthors()
}