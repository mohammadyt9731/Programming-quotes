package com.example.programmingquotes.core.data.network

import retrofit2.Response

fun <T> Response<T>.bodyOrThrow(): T? {
    if (this.isSuccessful) {
        return body()
    } else {
        throw ServerException(code = this.code(), messageError = this.message())
    }
}
