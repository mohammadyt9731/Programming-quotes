package com.example.programmingquotes.core.data.network

import com.example.programmingquotes.R
import com.example.programmingquotes.core.common.Errors
import com.example.programmingquotes.core.common.ResultWrapper
import java.net.ConnectException

internal suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: Exception) {
        when (e) {
            is ConnectException -> {
                Errors.Network(msg = R.string.msg_no_internet.toString())
            }
            is ServerException -> {
                Errors.Http(msg = e.messageError, code = e.code)
            }
            else -> {
                Errors.App(msg = e.message.toString())
            }
        }
    }
}
