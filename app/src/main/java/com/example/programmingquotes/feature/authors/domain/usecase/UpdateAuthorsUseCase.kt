package com.example.programmingquotes.feature.authors.domain.usecase

import com.example.programmingquotes.feature.authors.domain.AuthorRepository
import javax.inject.Inject

internal class UpdateAuthorsUseCase @Inject constructor(private val repository: AuthorRepository) {

    suspend operator fun invoke() = repository.fetchAuthorsAndInsertToDb()
}