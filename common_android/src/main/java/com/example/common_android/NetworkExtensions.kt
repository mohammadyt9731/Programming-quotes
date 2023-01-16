package com.example.common_android

import retrofit2.Response

internal fun <T> Response<T>.bodyOrThrow(): T {
    if (isSuccessful) {
        return body()!!
    } else {
        throw ServerException(code = code(), messageError = message())
    }
}
