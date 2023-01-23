package com.example.common.android

import com.example.base.Errors
import com.example.base.ResultWrapper
import com.example.common.android.R
import java.net.ConnectException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
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
