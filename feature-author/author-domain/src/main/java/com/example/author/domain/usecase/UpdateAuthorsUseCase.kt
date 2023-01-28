package com.example.author.domain.usecase

import com.example.author.domain.AuthorRepository
import javax.inject.Inject

class UpdateAuthorsUseCase @Inject constructor(private val repository: AuthorRepository) {
    suspend operator fun invoke() = repository.fetchAuthorsAndInsertToDb()
}