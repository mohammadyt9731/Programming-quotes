package com.example.programmingquotes.core.common

sealed class ResultWrapper<out T>{
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class HttpError(val code: Int?, val message: String?) : ResultWrapper<Nothing>()
    data class ApplicationError(val message: String?) : ResultWrapper<Nothing>()
    data class NetworkError(val message: String?= null) : ResultWrapper<Nothing>()
}
