package com.example.programmingquotes.core.common

sealed class ResultWrapper<out T> {
    object UnInitialize : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(
        val type: ErrorType,
        val message: String? = null,
        val code: Int? = null,
        val stringResId: Int? = null
    ) :
        ResultWrapper<Nothing>()
}

enum class ErrorType {
    HTTP,
    APP,
    NETWORK
}