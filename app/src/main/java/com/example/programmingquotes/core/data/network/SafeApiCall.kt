package com.example.programmingquotes.core.data.network

import com.example.programmingquotes.core.common.ResultWrapper


suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (e: Exception) {
        when (e) {
            is ServerException -> ResultWrapper.HttpError(
                message = e.messageError,
                code = e.code
            )
            else -> {
                ResultWrapper.ApplicationError(message = e.message.toString())
            }
        }
    }
}
