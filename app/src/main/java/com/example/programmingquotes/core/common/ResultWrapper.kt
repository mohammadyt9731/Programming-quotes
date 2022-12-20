package com.example.programmingquotes.core.common

internal sealed class ResultWrapper<out T> {
    object UnInitialize : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    class Error<T>(msg: String) : Errors<T>(msg)
}

internal sealed class Errors<T>(val message: String) : ResultWrapper<T>() {
    data class Http<T>(val msg: String, val code: Int) : Errors<T>(msg)
    data class App<T>(val msg: String) : Errors<T>(msg)
    data class Network<T>(val msg: String) : Errors<T>(msg)
}