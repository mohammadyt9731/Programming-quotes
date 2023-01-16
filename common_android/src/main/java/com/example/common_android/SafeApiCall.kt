package com.example.common_android

import com.example.common.Errors
import com.example.common.ResultWrapper
import java.net.ConnectException

internal suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: Exception) {
        when (e) {
            is ConnectException -> {
                ResultWrapper.Error(Errors.Network(msg = R.string.msg_no_internet.toString()))
            }
            is ServerException -> {
                ResultWrapper.Error(Errors.Http(msg = e.messageError, code = e.code))
            }
            else -> {
                ResultWrapper.Error(Errors.App(msg = e.message.toString()))
            }
        }
    }
}
