package com.example.programmingquotes.core.data.network

import com.example.programmingquotes.core.common.ErrorType
import com.example.programmingquotes.core.common.ResultWrapper

internal suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall.invoke())
    } catch (e: Exception) {
        when (e) {
            is ServerException -> ResultWrapper.Error(
                type = ErrorType.HTTP,
                message = e.messageError,
                code = e.code
            )
            else -> {
                ResultWrapper.Error(
                    type = ErrorType.APP, message = e.message.toString()
                )
            }
        }
    }
}
