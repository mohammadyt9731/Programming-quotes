package com.example.author_domain.usecase

import com.example.programmingquotes.feature.authors.domain.AuthorRepository
import javax.inject.Inject

class UpdateAuthorsUseCase @Inject constructor(private val repository: AuthorRepository) {
    suspend operator fun invoke() = repository.fetchAuthorsAndInsertToDb()
}