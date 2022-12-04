package com.example.programmingquotes.core.common

sealed class MyResponse<out T>(status: Status, data: T? = null, message: String? = null) {

    class Loading<T>() : MyResponse<T>(status = Status.Loading)
    class Success<T>(data: T) : MyResponse<T>(status = Status.Success, data = data)
    class Error<T>(message: String) : MyResponse<T>(status = Status.Error, message = message)
}

enum class Status {
    Loading,
    Success,
    Error
}