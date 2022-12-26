package com.example.programmingquotes.core.common

internal sealed class ResultWrapper<out T> {
    object UnInitialize : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val errors: Errors) : ResultWrapper<Nothing>()
}

internal sealed class Errors(val message: String) {
    data class Http(val msg: String, val code: Int) : Errors(msg)
    data class App(val msg: String) : Errors(msg)
    data class Network(val msg: String) : Errors(msg)
}